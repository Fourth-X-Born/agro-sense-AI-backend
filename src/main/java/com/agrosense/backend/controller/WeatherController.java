package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.WeatherResponse;
import com.agrosense.backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public ApiResponse<WeatherResponse> getWeather(@RequestParam("districtId") Long districtId) {
        WeatherResponse weather = weatherService.getWeatherForDistrict(districtId);
        return new ApiResponse<>(true, "Weather data fetched successfully", weather);
    }
}
