package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.WeatherAlert;
import com.agrosense.backend.dto.WeatherResponse;
import com.agrosense.backend.service.WeatherAlertService;
import com.agrosense.backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        // 1. Heat Wave Alert
        if (weather.getMain() != null && weather.getMain().getTemp() > 35.0) {
            alerts.add(WeatherAlert.builder()
                    .alertType("HEAT_WAVE")
                    .severity("HIGH")
                    .message("Extreme heat conditions detected (>" + weather.getMain().getTemp() + "°C).")
                    .precautions(List.of("Ensure proper irrigation", "Avoid spraying chemicals in midday sun"))
                    .build());
        }

        // 2. High Humidity / Pest Risk
        if (weather.getMain() != null && weather.getMain().getHumidity() > 90.0) {
            alerts.add(WeatherAlert.builder()
                    .alertType("PEST_RISK")
                    .severity("MEDIUM")
                    .message("High humidity detected (" + weather.getMain().getHumidity()
                            + "%). Risk of fungal diseases.")
                    .precautions(List.of("Monitor crops for fungal growth", "Ensure good air circulation"))
                    .build());
        }

        // 3. Heavy Rain Alert
        if (weather.getWeather() != null && !weather.getWeather().isEmpty()) {
            for (WeatherResponse.Weather w : weather.getWeather()) {
                if (w.getMain().equalsIgnoreCase("Rain")) {
                    alerts.add(WeatherAlert.builder()
                            .alertType("HEAVY_RAIN")
                            .severity("MEDIUM")
                            .message("Rainfall detected. Ensure drainage systems are clear.")
                            .precautions(List.of("Check drainage", "Delay fertilizer application"))
                            .build());
                }
            }
        }

        // 4. Strong Wind Alert
        if (weather.getWind() != null && weather.getWind().getSpeed() > 20.0) {
            alerts.add(WeatherAlert.builder()
                    .alertType("STRONG_WIND")
                    .severity("MEDIUM")
                    .message("Strong winds detected (" + weather.getWind().getSpeed() + " km/h).")
                    .precautions(List.of("Support tall crops", "Avoid spraying"))
                    .build());
        }

        if (alerts.isEmpty()) {
            alerts.add(WeatherAlert.builder()
                    .alertType("NONE")
                    .severity("LOW")
                    .message("No significant weather alerts.")
                    .precautions(List.of("Continue routine care"))
                    .build());
        }

        return alerts;
    }
}
