package com.agrosense.backend.dto;

public record FertilizerRecommendationDto(
        Long id,
        Long cropId,
        String cropName,
        String fertilizerName,
        String fertilizerType,
        String dosagePerHectare,
        String applicationStage,
        String applicationMethod,
        String notes) {
}
