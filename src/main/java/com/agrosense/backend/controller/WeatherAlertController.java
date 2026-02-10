package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.WeatherAlert;
import com.agrosense.backend.service.WeatherAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherAlertController {

    private final WeatherAlertService weatherAlertService;

    @GetMapping("/alerts")
    public ApiResponse<List<WeatherAlert>> getWeatherAlerts(@RequestParam Long districtId) {
        List<WeatherAlert> alerts = weatherAlertService.getAlertsForDistrict(districtId);
        return new ApiResponse<>(true, "Weather alerts fetched", alerts);
    }
}
