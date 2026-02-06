package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.FertilizerRecommendationDto;
import com.agrosense.backend.service.FertilizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fertilizers")
@RequiredArgsConstructor
public class FertilizerController {

    private final FertilizerService fertilizerService;

    @GetMapping
    public ApiResponse<List<FertilizerRecommendationDto>> getFertilizerRecommendations(
            @RequestParam(required = false) Long cropId,
            @RequestParam(required = false) String type) {

        List<FertilizerRecommendationDto> recommendations;

        if (cropId != null && type != null) {
            recommendations = fertilizerService.getRecommendationsByCropAndType(cropId, type);
        } else if (cropId != null) {
            recommendations = fertilizerService.getRecommendationsByCrop(cropId);
        } else if (type != null) {
            recommendations = fertilizerService.getRecommendationsByType(type);
        } else {
            recommendations = fertilizerService.getAllRecommendations();
        }

        return new ApiResponse<>(true, "Fertilizer recommendations fetched", recommendations);
    }
}
