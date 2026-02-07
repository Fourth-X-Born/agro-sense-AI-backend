package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.risk.*;
import com.agrosense.backend.dto.WeatherResponse;
import com.agrosense.backend.entity.Crop;
import com.agrosense.backend.entity.District;
import com.agrosense.backend.entity.RiskAnalysisHistory;
import com.agrosense.backend.repository.CropRepository;
import com.agrosense.backend.repository.DistrictRepository;
import com.agrosense.backend.repository.RiskAnalysisHistoryRepository;
import com.agrosense.backend.service.RiskService;
import com.agrosense.backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiskServiceImpl implements RiskService {

    private final CropRepository cropRepository;
    private final DistrictRepository districtRepository;
    private final RiskAnalysisHistoryRepository historyRepository;
    private final WeatherService weatherService;

    @Override
    public RiskAnalyzeResponse analyze(RiskAnalyzeRequest request) {

        if (request.getCropId() == null || request.getDistrictId() == null) {
            throw new IllegalArgumentException("cropId and districtId are required");
        }

        Crop crop = cropRepository.findById(request.getCropId())
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        // Use existing WeatherService to get current weather
        WeatherResponse weather = weatherService.getWeatherForDistrict(district.getId());

        // Extract current temperature and humidity
        double currentTemp = weather.getMain().getTemp();
        double humidity = weather.getMain().getHumidity();

        // Estimate rain based on weather description and humidity
        // (Since OpenWeatherMap current weather doesn't provide rain forecast,
        // we use humidity as a proxy for rain likelihood)
        double estimatedRainRisk = estimateRainFromHumidity(humidity, weather);

        // ---------- RULE-BASED SCORING (AI-like decision engine) ----------
        int humidityScore = scoreHumidityRisk(humidity);
        int tempScore = scoreTempRisk(currentTemp);

        int cropFactor = cropSensitivityFactor(crop.getName()); // 0–20
        int score = clamp(humidityScore + tempScore + cropFactor, 0, 100);

        String level = scoreToLevel(score);

        List<String> explanation = buildExplanation(crop.getName(), currentTemp, humidity, score, level, weather);
        List<String> recommendations = buildRecommendations(crop.getName(), currentTemp, humidity, level);

        // ---------- SAVE HISTORY ----------
        RiskAnalysisHistory saved = historyRepository.save(
                RiskAnalysisHistory.builder()
                        .farmerId(request.getFarmerId())
                        .cropId(crop.getId())
                        .districtId(district.getId())
                        .cropName(crop.getName())
                        .districtName(district.getName())
                        .avgTempC(currentTemp)
                        .totalRainMmNext7Days(estimatedRainRisk) // Using humidity-based estimate
                        .riskLevel(level)
                        .riskScore(score)
                        .explanation(String.join(" | ", explanation))
                        .recommendations(String.join(" | ", recommendations))
                        .createdAt(LocalDateTime.now())
                        .build());

        return RiskAnalyzeResponse.builder()
                .historyId(saved.getId())
                .cropId(crop.getId())
                .cropName(crop.getName())
                .districtId(district.getId())
                .districtName(district.getName())
                .riskScore(score)
                .riskLevel(level)
                .avgTempC(currentTemp)
                .totalRainMmNext7Days(estimatedRainRisk)
                .explanation(explanation)
                .recommendations(recommendations)
                .build();
    }

    @Override
    public List<RiskHistoryItemDto> history(Long farmerId) {
        if (farmerId == null)
            throw new IllegalArgumentException("farmerId is required");

        return historyRepository.findByFarmerIdOrderByCreatedAtDesc(farmerId)
                .stream()
                .map(h -> RiskHistoryItemDto.builder()
                        .id(h.getId())
                        .farmerId(h.getFarmerId())
                        .cropId(h.getCropId())
                        .cropName(h.getCropName())
                        .districtId(h.getDistrictId())
                        .districtName(h.getDistrictName())
                        .riskScore(h.getRiskScore())
                        .riskLevel(h.getRiskLevel())
                        .avgTempC(h.getAvgTempC())
                        .totalRainMmNext7Days(h.getTotalRainMmNext7Days())
                        .createdAt(h.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // ----------------- scoring helpers -----------------

    private double estimateRainFromHumidity(double humidity, WeatherResponse weather) {
        // Check if weather description indicates rain
        String weatherDesc = "";
        if (weather.getWeather() != null && !weather.getWeather().isEmpty()) {
            weatherDesc = weather.getWeather().get(0).getDescription().toLowerCase();
        }

        // If description indicates rain/drizzle/thunderstorm, estimate higher
        if (weatherDesc.contains("rain") || weatherDesc.contains("drizzle")) {
            return humidity * 1.5; // Estimated mm based on humidity
        } else if (weatherDesc.contains("thunderstorm")) {
            return humidity * 2.0;
        } else if (humidity >= 80) {
            return humidity * 0.8; // High humidity indicates possible rain
        } else if (humidity >= 60) {
            return humidity * 0.3;
        }
        return 0; // Low humidity = unlikely rain
    }

    private int scoreHumidityRisk(double humidity) {
        // High humidity = higher disease/pest risk
        if (humidity >= 85)
            return 45;
        if (humidity >= 70)
            return 30;
        if (humidity >= 50)
            return 15;
        return 5;
    }

    private int scoreTempRisk(double avgTempC) {
        // Temperature stress model
        if (avgTempC >= 35)
            return 40; // extreme heat stress
        if (avgTempC >= 32)
            return 30; // heat stress
        if (avgTempC >= 28)
            return 20;
        if (avgTempC >= 24)
            return 10;
        if (avgTempC < 15)
            return 25; // cold stress
        return 5;
    }

    private int cropSensitivityFactor(String cropName) {
        String c = cropName == null ? "" : cropName.toLowerCase();

        // Tomato is more disease-sensitive in humid conditions
        if (c.contains("tomato"))
            return 20;
        // Rice tolerates wet more, but continuous humidity affects pests/disease
        if (c.contains("rice"))
            return 10;
        // Maize moderate
        if (c.contains("maize"))
            return 12;

        return 15; // default moderate sensitivity
    }

    private String scoreToLevel(int score) {
        if (score >= 70)
            return "HIGH";
        if (score >= 40)
            return "MEDIUM";
        return "LOW";
    }

    private List<String> buildExplanation(String crop, double temp, double humidity,
            int score, String level, WeatherResponse weather) {
        List<String> reasons = new ArrayList<>();
        reasons.add("Risk assessment based on current weather conditions.");
        reasons.add("Current temperature: " + round1(temp) + "°C.");
        reasons.add("Current humidity: " + round1(humidity) + "%.");

        if (weather.getWeather() != null && !weather.getWeather().isEmpty()) {
            reasons.add("Weather condition: " + weather.getWeather().get(0).getDescription() + ".");
        }
        if (weather.getWind() != null) {
            reasons.add("Wind speed: " + round1(weather.getWind().getSpeed()) + " m/s.");
        }

        reasons.add("Crop sensitivity factor applied for: " + crop + ".");
        reasons.add("Computed risk score: " + score + "/100 → " + level + " risk.");
        return reasons;
    }

    private List<String> buildRecommendations(String crop, double temp, double humidity, String level) {
        List<String> rec = new ArrayList<>();

        if ("HIGH".equals(level)) {
            rec.add("Avoid fertilizer application during high humidity periods to reduce nutrient loss.");
            rec.add("Improve drainage and ensure proper air circulation to reduce disease pressure.");
            rec.add("Monitor for pests/diseases daily and apply recommended control measures early.");
        } else if ("MEDIUM".equals(level)) {
            rec.add("Check drainage and maintain soil moisture balance.");
            rec.add("Monitor crop health every 2–3 days; watch for early symptoms.");
            rec.add("Plan irrigation/fertilizer timing based on weather conditions.");
        } else {
            rec.add("Maintain standard crop care and monitor weekly.");
            rec.add("Follow recommended fertilizer schedule and irrigation plan.");
        }

        // Extra crop-specific recommendations
        String c = crop == null ? "" : crop.toLowerCase();
        if (c.contains("tomato") && humidity >= 70) {
            rec.add("Tomato: high humidity can increase fungal risk — consider preventive fungicide application.");
        }
        if (c.contains("rice") && temp >= 32) {
            rec.add("Rice: heat stress possible — ensure sufficient water availability in paddy fields.");
        }
        if (c.contains("maize") && humidity >= 80) {
            rec.add("Maize: high humidity may promote ear rot — monitor closely during grain fill stage.");
        }

        return rec;
    }

    private int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    private String round1(double v) {
        return String.format(Locale.US, "%.1f", v);
    }
}
