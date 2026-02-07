package com.agrosense.backend.service;

import com.agrosense.backend.dto.WeatherResponse;

public interface WeatherService {
    WeatherResponse getWeatherForDistrict(Long districtId);
}
