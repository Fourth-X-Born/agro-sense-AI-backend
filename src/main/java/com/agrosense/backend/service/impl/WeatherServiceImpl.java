package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.DailyForecast;
import com.agrosense.backend.dto.ForecastResponse;
import com.agrosense.backend.dto.WeatherResponse;
import com.agrosense.backend.entity.District;
import com.agrosense.backend.exception.ResourceNotFoundException;
import com.agrosense.backend.repository.DistrictRepository;
import com.agrosense.backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;
    private final DistrictRepository districtRepository;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Override
    public WeatherResponse getWeatherForDistrict(Long districtId) {
        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id: " + districtId));

        String districtName = district.getName();
        log.info("Fetching weather for district: {}", districtName);

        String url = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("q", districtName + ",LK") // Append country code for Sri Lanka
                .queryParam("units", "metric")
                .queryParam("appid", apiKey)
                .toUriString();

        try {
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (Exception e) {
            log.error("Error fetching weather data: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
    }

    @Override
    public List<DailyForecast> getForecastForDistrict(Long districtId) {
        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id: " + districtId));

        String districtName = district.getName();
        log.info("Fetching forecast for district: {}", districtName);

        // Use OpenWeatherMap's 5-day/3-hour forecast API
        String forecastUrl = apiUrl.replace("/weather", "/forecast");
        String url = UriComponentsBuilder.fromUriString(forecastUrl)
                .queryParam("q", districtName + ",LK")
                .queryParam("units", "metric")
                .queryParam("appid", apiKey)
                .toUriString();

        try {
            ForecastResponse response = restTemplate.getForObject(url, ForecastResponse.class);
            return processForecastData(response);
        } catch (Exception e) {
            log.error("Error fetching forecast data: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch forecast data: " + e.getMessage());
        }
    }

    private List<DailyForecast> processForecastData(ForecastResponse response) {
        if (response == null || response.getList() == null) {
            return Collections.emptyList();
        }

        // Group forecast items by date
        Map<LocalDate, List<ForecastResponse.ForecastItem>> groupedByDate = response.getList().stream()
                .collect(Collectors.groupingBy(item -> Instant.ofEpochSecond(item.getDt())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()));

        List<DailyForecast> dailyForecasts = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // Sort dates and process each day (up to 7 days)
        groupedByDate.keySet().stream()
                .sorted()
                .limit(7)
                .forEach(date -> {
                    List<ForecastResponse.ForecastItem> items = groupedByDate.get(date);

                    // Find min and max temperatures for the day
                    double minTemp = items.stream()
                            .mapToDouble(i -> i.getMain().getTempMin())
                            .min()
                            .orElse(0);
                    double maxTemp = items.stream()
                            .mapToDouble(i -> i.getMain().getTempMax())
                            .max()
                            .orElse(0);

                    // Get the most common weather condition for the day (use midday if available)
                    ForecastResponse.ForecastItem representativeItem = items.stream()
                            .filter(i -> i.getDtTxt().contains("12:00"))
                            .findFirst()
                            .orElse(items.get(0));

                    String weatherMain = representativeItem.getWeather().isEmpty()
                            ? "Clear"
                            : representativeItem.getWeather().get(0).getMain();
                    String description = representativeItem.getWeather().isEmpty()
                            ? "Clear"
                            : representativeItem.getWeather().get(0).getDescription();

                    // Determine day name
                    String dayName;
                    if (date.equals(today)) {
                        dayName = "Today";
                    } else if (date.equals(today.plusDays(1))) {
                        dayName = "Tomorrow";
                    } else {
                        dayName = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                    }

                    // Map weather to Material icon
                    String icon = mapWeatherToIcon(weatherMain);

                    dailyForecasts.add(DailyForecast.builder()
                            .day(dayName)
                            .date(date.getMonthValue() + "/" + date.getDayOfMonth())
                            .icon(icon)
                            .high((int) Math.round(maxTemp))
                            .low((int) Math.round(minTemp))
                            .description(description)
                            .build());
                });

        return dailyForecasts;
    }

    private String mapWeatherToIcon(String weatherMain) {
        return switch (weatherMain.toLowerCase()) {
            case "clear" -> "sunny";
            case "clouds" -> "cloud";
            case "rain", "drizzle" -> "rainy";
            case "thunderstorm" -> "thunderstorm";
            case "snow" -> "ac_unit";
            case "mist", "fog", "haze" -> "foggy";
            default -> "partly_cloudy_day";
        };
    }
}
