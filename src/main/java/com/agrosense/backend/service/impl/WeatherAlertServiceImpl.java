package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.WeatherAlert;
import com.agrosense.backend.dto.WeatherResponse;
import com.agrosense.backend.service.WeatherAlertService;
import com.agrosense.backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherAlertServiceImpl implements WeatherAlertService {

    private final WeatherService weatherService;

    @Override
    public List<WeatherAlert> getAlertsForDistrict(Long districtId) {
        WeatherResponse weather = weatherService.getWeatherForDistrict(districtId);
        List<WeatherAlert> alerts = new ArrayList<>();

        if (weather == null)
            return alerts;

        LocalDateTime now = LocalDateTime.now();

        // 1. Heat Wave Alert
        if (weather.getMain() != null && weather.getMain().getTemp() > 35.0) {
            alerts.add(WeatherAlert.builder()
                    .alertType("HEAT_WAVE")
                    .severity("HIGH")
                    .icon("local_fire_department")
                    .message("Extreme heat conditions detected (" + Math.round(weather.getMain().getTemp())
                            + "°C). Risk of heat stress for crops and livestock.")
                    .precautions(List.of("Ensure proper irrigation", "Provide shade for livestock",
                            "Avoid spraying chemicals in midday sun"))
                    .validUntil(formatValidUntil(now.plusHours(12)))
                    .build());
        }

        // 2. High Humidity / Pest Risk
        if (weather.getMain() != null && weather.getMain().getHumidity() > 85.0) {
            alerts.add(WeatherAlert.builder()
                    .alertType("PEST_RISK")
                    .severity("MEDIUM")
                    .icon("bug_report")
                    .message("High humidity detected (" + Math.round(weather.getMain().getHumidity())
                            + "%). Risk of fungal diseases and pest activity.")
                    .precautions(List.of("Monitor crops for fungal growth", "Ensure good air circulation",
                            "Apply preventive fungicides if needed"))
                    .validUntil(formatValidUntil(now.plusHours(24)))
                    .build());
        }

        // 3. Heavy Rain Alert
        if (weather.getWeather() != null && !weather.getWeather().isEmpty()) {
            for (WeatherResponse.Weather w : weather.getWeather()) {
                String main = w.getMain().toLowerCase();
                if (main.contains("rain") || main.contains("drizzle")) {
                    String severity = main.contains("heavy") ? "HIGH" : "MEDIUM";
                    alerts.add(WeatherAlert.builder()
                            .alertType("HEAVY_RAIN")
                            .severity(severity)
                            .icon("thunderstorm")
                            .message(
                                    "Rainfall detected: " + w.getDescription() + ". Ensure drainage systems are clear.")
                            .precautions(List.of("Check drainage canals", "Delay fertilizer application",
                                    "Protect harvested crops"))
                            .validUntil(formatValidUntil(now.plusHours(6)))
                            .build());
                    break;
                }
                if (main.contains("thunderstorm")) {
                    alerts.add(WeatherAlert.builder()
                            .alertType("THUNDERSTORM")
                            .severity("HIGH")
                            .icon("thunderstorm")
                            .message("Thunderstorm detected. Seek shelter and avoid outdoor farming activities.")
                            .precautions(List.of("Stay indoors", "Avoid open fields", "Secure loose equipment"))
                            .validUntil(formatValidUntil(now.plusHours(3)))
                            .build());
                    break;
                }
            }
        }

        // 4. Strong Wind Alert
        if (weather.getWind() != null && weather.getWind().getSpeed() > 10.0) { // Speed is in m/s, 10 m/s = 36 km/h
            String severity = weather.getWind().getSpeed() > 15.0 ? "HIGH" : "MEDIUM";
            alerts.add(WeatherAlert.builder()
                    .alertType("STRONG_WIND")
                    .severity(severity)
                    .icon("air")
                    .message("Strong winds detected (" + Math.round(weather.getWind().getSpeed() * 3.6)
                            + " km/h). Protect fragile crops.")
                    .precautions(List.of("Support tall crops and plants", "Avoid spraying pesticides",
                            "Secure greenhouse covers"))
                    .validUntil(formatValidUntil(now.plusHours(6)))
                    .build());
        }

        // 5. Low Visibility / Fog Alert
        if (weather.getWeather() != null && !weather.getWeather().isEmpty()) {
            for (WeatherResponse.Weather w : weather.getWeather()) {
                String main = w.getMain().toLowerCase();
                if (main.contains("mist") || main.contains("fog") || main.contains("haze")) {
                    alerts.add(WeatherAlert.builder()
                            .alertType("LOW_VISIBILITY")
                            .severity("LOW")
                            .icon("foggy")
                            .message("Low visibility conditions: " + w.getDescription()
                                    + ". Take care during transport.")
                            .precautions(List.of("Use headlights when driving", "Delay outdoor activities if needed"))
                            .validUntil(formatValidUntil(now.plusHours(6)))
                            .build());
                    break;
                }
            }
        }

        // If no alerts, show "all clear"
        if (alerts.isEmpty()) {
            alerts.add(WeatherAlert.builder()
                    .alertType("NONE")
                    .severity("LOW")
                    .icon("check_circle")
                    .message("No significant weather alerts. Conditions are favorable for farming activities.")
                    .precautions(List.of("Continue routine care", "Monitor weather updates"))
                    .validUntil(null)
                    .build());
        }

        return alerts;
    }

    private String formatValidUntil(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        if (dateTime.toLocalDate().equals(now.toLocalDate())) {
            return "Until " + dateTime.format(timeFormatter) + " Today";
        } else if (dateTime.toLocalDate().equals(now.toLocalDate().plusDays(1))) {
            return "Until " + dateTime.format(timeFormatter) + " Tomorrow";
        } else {
            DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("h:mm a, MMM d");
            return "Until " + dateTime.format(fullFormatter);
        }
    }
}
