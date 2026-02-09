package com.agrosense.backend.service;

import com.agrosense.backend.dto.DailyForecast;
import com.agrosense.backend.dto.WeatherResponse;

import java.util.List;

public interface WeatherService {
    WeatherResponse getWeatherForDistrict(Long districtId);

    List<DailyForecast> getForecastForDistrict(Long districtId);
}
