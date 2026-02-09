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

import java.util.Map;

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

    // Geo-coordinates for districts that OpenWeatherMap doesn't recognize by name
    private static final Map<String, double[]> DISTRICT_COORDINATES = Map.ofEntries(
            Map.entry("Nuwara Eliya", new double[] { 6.9497, 80.7891 }),
            Map.entry("Kilinochchi", new double[] { 9.3803, 80.3770 }),
            Map.entry("Mullaitivu", new double[] { 9.2671, 80.8142 }),
            Map.entry("Monaragala", new double[] { 6.8728, 81.3507 }),
            Map.entry("Kegalle", new double[] { 7.2513, 80.3464 }),
            Map.entry("Polonnaruwa", new double[] { 7.9403, 81.0188 }),
            Map.entry("Vavuniya", new double[] { 8.7514, 80.4971 }));

    @Override
    public WeatherResponse getWeatherForDistrict(Long districtId) {
        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id: " + districtId));

        String districtName = district.getName();
        log.info("Fetching weather for district: {}", districtName);

        String url;

        // Check if we need to use geo-coordinates for this district
        if (DISTRICT_COORDINATES.containsKey(districtName)) {
            double[] coords = DISTRICT_COORDINATES.get(districtName);
            url = UriComponentsBuilder.fromUriString(apiUrl)
                    .queryParam("lat", coords[0])
                    .queryParam("lon", coords[1])
                    .queryParam("units", "metric")
                    .queryParam("appid", apiKey)
                    .toUriString();
            log.info("Using geo-coordinates for {}: lat={}, lon={}", districtName, coords[0], coords[1]);
        } else {
            // Use district name for API query
            url = UriComponentsBuilder.fromUriString(apiUrl)
                    .queryParam("q", districtName + ",LK")
                    .queryParam("units", "metric")
                    .queryParam("appid", apiKey)
                    .toUriString();
        }

        try {
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (Exception e) {
            log.error("Error fetching weather data for {}: {}", districtName, e.getMessage());
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
    }
}
