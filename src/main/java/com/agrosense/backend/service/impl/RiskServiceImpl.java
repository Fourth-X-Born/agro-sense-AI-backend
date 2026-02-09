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

        WeatherResponse weather = weatherService.getWeatherForDistrict(district.getId());

        double temp = weather.getMain().getTemp();
        double humidity = weather.getMain().getHumidity();
        double windSpeed = weather.getWind() != null ? weather.getWind().getSpeed() : 0;
        String weatherCondition = "";
        String weatherMain = "";
        if (weather.getWeather() != null && !weather.getWeather().isEmpty()) {
            weatherCondition = weather.getWeather().get(0).getDescription().toLowerCase();
            weatherMain = weather.getWeather().get(0).getMain().toLowerCase();
        }

        double rainMm = 0;
        if (weatherMain.contains("rain") || weatherMain.contains("drizzle")) {
            rainMm = humidity >= 80 ? 10 : 5;
        } else if (weatherMain.contains("thunderstorm")) {
            rainMm = 20;
        }

        int score = calculateRiskScore(crop.getName(), temp, humidity, windSpeed, weatherMain, rainMm);
        String level = scoreToLevel(score);

        List<String> explanation = buildExplanation(crop.getName(), district.getName(), temp, humidity, windSpeed,
                weatherCondition, score, level);
        List<String> recommendations = build6Recommendations(crop.getName(), temp, humidity, windSpeed, weatherMain,
                level);

        RiskAnalysisHistory saved = historyRepository.save(
                RiskAnalysisHistory.builder()
                        .farmerId(request.getFarmerId())
                        .cropId(crop.getId())
                        .districtId(district.getId())
                        .cropName(crop.getName())
                        .districtName(district.getName())
                        .avgTempC(temp)
                        .totalRainMmNext7Days(rainMm)
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
                .avgTempC(temp)
                .totalRainMmNext7Days(rainMm)
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

    // ================= RISK SCORING =================

    private int calculateRiskScore(String cropName, double temp, double humidity, double windSpeed, String weatherMain,
            double rainMm) {
        int score = 0;
        String crop = cropName.toLowerCase();

        score += calculateTempRisk(crop, temp);
        score += calculateHumidityRisk(crop, humidity);
        score += calculateWeatherConditionRisk(weatherMain, rainMm);
        score += calculateWindRisk(windSpeed);
        score += getCropSensitivity(crop);

        return clamp(score, 0, 100);
    }

    private int calculateTempRisk(String crop, double temp) {
        if (crop.contains("rice") || crop.contains("paddy")) {
            if (temp < 15)
                return 30;
            if (temp < 20)
                return 20;
            if (temp > 38)
                return 30;
            if (temp > 35)
                return 20;
            if (temp >= 25 && temp <= 32)
                return 0;
            return 10;
        } else if (crop.contains("tomato")) {
            if (temp < 10)
                return 30;
            if (temp < 15)
                return 20;
            if (temp > 35)
                return 30;
            if (temp > 30)
                return 20;
            if (temp >= 18 && temp <= 27)
                return 0;
            return 10;
        } else if (crop.contains("maize")) {
            if (temp < 10)
                return 30;
            if (temp < 15)
                return 20;
            if (temp > 38)
                return 30;
            if (temp > 35)
                return 20;
            if (temp >= 21 && temp <= 30)
                return 0;
            return 10;
        }
        if (temp < 10 || temp > 40)
            return 30;
        if (temp < 15 || temp > 35)
            return 20;
        return 5;
    }

    private int calculateHumidityRisk(String crop, double humidity) {
        if (crop.contains("tomato")) {
            if (humidity >= 90)
                return 25;
            if (humidity >= 80)
                return 20;
            if (humidity >= 70)
                return 15;
            if (humidity < 40)
                return 10;
            return 5;
        } else if (crop.contains("rice")) {
            if (humidity >= 95)
                return 15;
            if (humidity >= 85)
                return 10;
            if (humidity < 50)
                return 15;
            return 5;
        }
        if (humidity >= 90)
            return 20;
        if (humidity >= 80)
            return 15;
        if (humidity >= 70)
            return 10;
        return 5;
    }

    private int calculateWeatherConditionRisk(String weatherMain, double rainMm) {
        if (weatherMain.contains("thunderstorm"))
            return 25;
        if (weatherMain.contains("rain") && rainMm > 10)
            return 20;
        if (weatherMain.contains("rain"))
            return 15;
        if (weatherMain.contains("drizzle"))
            return 10;
        if (weatherMain.contains("mist") || weatherMain.contains("fog"))
            return 10;
        if (weatherMain.contains("clear"))
            return 0;
        if (weatherMain.contains("clouds"))
            return 5;
        return 5;
    }

    private int calculateWindRisk(double windSpeed) {
        if (windSpeed > 15)
            return 10;
        if (windSpeed > 10)
            return 7;
        if (windSpeed > 5)
            return 3;
        return 0;
    }

    private int getCropSensitivity(String crop) {
        if (crop.contains("tomato"))
            return 10;
        if (crop.contains("tea"))
            return 8;
        if (crop.contains("maize"))
            return 6;
        if (crop.contains("rice"))
            return 4;
        return 5;
    }

    private String scoreToLevel(int score) {
        if (score >= 60)
            return "HIGH";
        if (score >= 35)
            return "MEDIUM";
        return "LOW";
    }

    // ================= EXPLANATION =================

    private List<String> buildExplanation(String crop, String district, double temp, double humidity,
            double windSpeed, String weatherCondition, int score, String level) {
        List<String> reasons = new ArrayList<>();

        reasons.add(String.format("Risk assessment based on current weather conditions."));
        reasons.add(String.format("Current temperature: %.1f°C.", temp));
        reasons.add(String.format("Current humidity: %.0f%%.", humidity));

        if (!weatherCondition.isEmpty()) {
            reasons.add(String.format("Weather condition: %s.", weatherCondition));
        }
        if (windSpeed > 0) {
            reasons.add(String.format("Wind speed: %.1f m/s.", windSpeed));
        }

        reasons.add(String.format("Crop sensitivity factor applied for: %s.", crop));
        reasons.add(String.format("Computed risk score: %d/100 → %s risk.", score, level));

        return reasons;
    }

    // ================= 6 RECOMMENDATIONS WITH DESCRIPTIONS =================

    private List<String> build6Recommendations(String cropName, double temp, double humidity,
            double windSpeed, String weatherMain, String level) {

        List<String> recommendations = new ArrayList<>();
        String crop = cropName.toLowerCase();
        boolean isRainy = weatherMain.contains("rain") || weatherMain.contains("drizzle")
                || weatherMain.contains("thunderstorm");

        // 1. IRRIGATION RECOMMENDATION
        String irrigation = getIrrigationRecommendation(temp, humidity, isRainy);
        recommendations.add(irrigation);

        // 2. PEST & DISEASE RECOMMENDATION
        String pestDisease = getPestDiseaseRecommendation(crop, temp, humidity);
        recommendations.add(pestDisease);

        // 3. FERTILIZER RECOMMENDATION
        String fertilizer = getFertilizerRecommendation(temp, humidity, isRainy);
        recommendations.add(fertilizer);

        // 4. CROP-SPECIFIC RECOMMENDATION
        String cropSpecific = getCropSpecificRecommendation(crop, temp, humidity, weatherMain);
        recommendations.add(cropSpecific);

        // 5. WEATHER ACTION RECOMMENDATION
        String weatherAction = getWeatherActionRecommendation(weatherMain, windSpeed, temp);
        recommendations.add(weatherAction);

        // 6. RISK LEVEL ACTION RECOMMENDATION
        String riskAction = getRiskLevelRecommendation(level, crop);
        recommendations.add(riskAction);

        return recommendations;
    }

    private String getIrrigationRecommendation(double temp, double humidity, boolean isRainy) {
        if (isRainy) {
            return "💧 Irrigation: Skip watering today as rainfall is expected. Check field drainage to prevent waterlogging and ensure excess water can drain away from root zones.";
        } else if (temp >= 35) {
            return "💧 Irrigation: Water twice daily during extreme heat - early morning (5-7 AM) and evening (5-7 PM). Apply 25-30% more water than usual to compensate for high evaporation rates.";
        } else if (temp >= 30) {
            return "💧 Irrigation: Increase watering frequency in warm weather. Best time is early morning to reduce evaporation. Check soil moisture by inserting finger 2 inches into soil before watering.";
        } else if (temp < 18) {
            return "💧 Irrigation: Reduce watering in cool weather to prevent root diseases. Water during mid-morning (9-11 AM) when soil is warmer for better absorption.";
        } else if (humidity < 50) {
            return "💧 Irrigation: Low humidity detected - maintain regular watering schedule and consider light foliar misting during hot afternoons to reduce plant stress.";
        }
        return "💧 Irrigation: Conditions are ideal for normal watering schedule. Water when the top 1-2 inches of soil feels dry. Early morning irrigation is most effective.";
    }

    private String getPestDiseaseRecommendation(String crop, double temp, double humidity) {
        if (humidity >= 85) {
            if (crop.contains("tomato")) {
                return "🦠 Disease Alert: Very high humidity creates perfect conditions for Late Blight and Bacterial Wilt in tomatoes. Apply Mancozeb (2.5g/L) or Copper Hydroxide spray immediately as preventive measure.";
            } else if (crop.contains("rice")) {
                return "🦠 Disease Alert: High humidity favors Rice Blast disease. Scout for brown/gray leaf spots and apply Tricyclazole (0.6g/L) as preventive treatment. Monitor for Brown Plant Hopper at plant bases.";
            } else if (crop.contains("maize")) {
                return "🦠 Disease Alert: High humidity increases risk of Northern Corn Leaf Blight and ear rot. Inspect plants for long gray lesions on leaves and check ears for discolored kernels.";
            }
            return "🦠 Disease Alert: Very high humidity creates ideal conditions for fungal diseases. Apply preventive fungicide (Mancozeb 2.5g/L) and ensure good air circulation by pruning dense foliage.";
        } else if (humidity >= 70) {
            return "🦠 Disease Watch: Moderate humidity levels - monitor plants every 2-3 days for early signs of fungal infection such as leaf spots, wilting, or unusual discoloration.";
        } else if (temp >= 28 && temp <= 35) {
            return "🦠 Pest Watch: Warm conditions favor insect pests. Check under leaves for aphids, whiteflies, and caterpillars. Use yellow sticky traps to monitor pest populations in your field.";
        }
        return "🦠 Pest & Disease: Low disease pressure expected under current conditions. Continue routine scouting weekly and maintain good field hygiene by removing plant debris.";
    }

    private String getFertilizerRecommendation(double temp, double humidity, boolean isRainy) {
        if (isRainy) {
            return "🌱 Fertilizer: Do NOT apply fertilizers during rain - nutrients will wash away causing pollution and waste. Wait 24-48 hours after rain stops before any fertilizer application.";
        } else if (temp >= 35) {
            return "🌱 Fertilizer: Avoid applying fertilizers during peak heat hours. Apply in early morning or late evening, and water immediately after granular application to prevent root burn.";
        } else if (humidity >= 85) {
            return "🌱 Fertilizer: Skip foliar fertilizers in high humidity as they may cause leaf burn. Use soil-applied granular fertilizers instead, and apply during dry morning hours.";
        }
        return "🌱 Fertilizer: Good conditions for fertilizer application. Follow your crop's nutrient schedule - use high-N fertilizers during vegetative stage, P for roots, and K during fruiting stage.";
    }

    private String getCropSpecificRecommendation(String crop, double temp, double humidity, String weatherMain) {
        if (crop.contains("rice") || crop.contains("paddy")) {
            if (temp >= 32) {
                return "🌾 Rice Care: High temperature may affect spikelet fertility during flowering. Maintain water level at 5cm in paddy fields and consider draining and refilling to lower water temperature.";
            }
            return "🌾 Rice Care: Maintain consistent 5cm water level in paddy field. Remove weeds competing for nutrients. Monitor for stem borers and apply Cartap if infestation exceeds threshold.";
        } else if (crop.contains("tomato")) {
            if (humidity >= 75) {
                return "🍅 Tomato Care: High humidity risk! Remove lower leaves touching soil, stake plants properly for air circulation, and avoid overhead watering. Use drip irrigation to keep foliage dry.";
            } else if (temp >= 32) {
                return "🍅 Tomato Care: Heat stress may cause flower drop. Install 30-40% shade net and apply calcium spray (Calcium Nitrate 5g/L) weekly to prevent Blossom End Rot on developing fruits.";
            }
            return "🍅 Tomato Care: Good growing conditions. Continue staking and pruning suckers for better air circulation. Check for Tomato Leaf Miner (serpentine tunnels) and apply Spinosad if found.";
        } else if (crop.contains("maize")) {
            if (temp >= 35) {
                return "🌽 Maize Care: Critical heat during tasseling can reduce pollination by 50%. Irrigate in evening to cool canopy temperature and ensure plants have adequate water during silking stage.";
            }
            return "🌽 Maize Care: Scout for Fall Armyworm caterpillars in leaf whorls - they cause significant damage. Apply Emamectin Benzoate if more than 5% plants affected. Side-dress with urea at knee-high stage.";
        } else if (crop.contains("tea")) {
            if (humidity >= 85) {
                return "🍵 Tea Care: High humidity increases Blister Blight risk on young leaves. Apply Hexaconazole or Copper-based fungicide. Maintain shade trees and 7-10 day plucking schedule for quality.";
            }
            return "🍵 Tea Care: Good tea growing conditions. Maintain regular plucking schedule (7-10 days) for quality production. Apply organic manure to improve soil health and tea flavor.";
        }
        return "🌿 Crop Care: Monitor your crop daily for any signs of stress, unusual leaf color, or pest damage. Remove weeds regularly as they compete for water and nutrients essential for your crop.";
    }

    private String getWeatherActionRecommendation(String weatherMain, double windSpeed, double temp) {
        if (weatherMain.contains("thunderstorm")) {
            return "⛈️ Storm Action: SAFETY FIRST - avoid field work during thunderstorms! After the storm passes, inspect crops for physical damage and broken stems. Apply wound-healing paste on damaged stems.";
        } else if (weatherMain.contains("rain")) {
            return "🌧️ Rainy Day Action: Postpone all spraying operations until weather clears. Check and clear drainage channels to prevent waterlogging. Plan to apply preventive fungicide 24-48 hours after rain stops.";
        } else if (windSpeed > 8) {
            return "💨 Windy Conditions: Secure tall plants with additional stakes (2-3 per plant). Do NOT spray pesticides in wind - causes drift and reduces effectiveness. Create windbreaks if possible.";
        } else if (temp >= 35) {
            return "☀️ Heat Wave Action: Apply organic mulch (straw, dried leaves) around plants to reduce soil temperature by 5-8°C and conserve moisture. Avoid any stressful operations during midday heat.";
        } else if (temp < 15) {
            return "❄️ Cold Weather Action: Protect sensitive crops with plastic covers or row covers overnight. Delay planting heat-loving crops until temperatures consistently rise above 18°C.";
        }
        return "🌤️ Good Weather: Excellent conditions for field activities! This is a good time for transplanting, weeding, fertilizing, pest scouting, or other maintenance tasks you've been planning.";
    }

    private String getRiskLevelRecommendation(String level, String crop) {
        if ("HIGH".equals(level)) {
            return "🚨 Action Required: HIGH RISK conditions detected! Inspect your fields DAILY and take immediate preventive action. Keep fungicides and pesticides ready for immediate application if symptoms appear.";
        } else if ("MEDIUM".equals(level)) {
            return "⚠️ Stay Vigilant: MEDIUM RISK level - inspect fields every 2-3 days for early symptoms. Have preventive sprays ready to apply if conditions worsen. Monitor weather forecasts closely.";
        }
        return "✅ All Clear: LOW RISK - conditions are favorable for " + crop
                + "! Continue normal farming practices with weekly monitoring. Use this good weather period to complete any pending farm maintenance.";
    }

    private int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }
}
