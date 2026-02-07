package com.agrosense.backend.service.impl;

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
}
