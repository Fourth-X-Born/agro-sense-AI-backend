package com.agrosense.backend.service;

import com.agrosense.backend.dto.FertilizerRecommendationDto;
import java.util.List;

public interface FertilizerService {

    List<FertilizerRecommendationDto> getAllRecommendations();

    List<FertilizerRecommendationDto> getRecommendationsByCrop(Long cropId);

    List<FertilizerRecommendationDto> getRecommendationsByType(String fertilizerType);

    List<FertilizerRecommendationDto> getRecommendationsByCropAndType(Long cropId, String fertilizerType);
}
