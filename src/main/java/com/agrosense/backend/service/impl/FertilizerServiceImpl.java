package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.FertilizerRecommendationDto;
import com.agrosense.backend.entity.FertilizerRecommendation;
import com.agrosense.backend.repository.FertilizerRecommendationRepository;
import com.agrosense.backend.service.FertilizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FertilizerServiceImpl implements FertilizerService {

    private final FertilizerRecommendationRepository fertilizerRepository;

    @Override
    public List<FertilizerRecommendationDto> getAllRecommendations() {
        return fertilizerRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<FertilizerRecommendationDto> getRecommendationsByCrop(Long cropId) {
        return fertilizerRepository.findByCropId(cropId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<FertilizerRecommendationDto> getRecommendationsByType(String fertilizerType) {
        return fertilizerRepository.findByFertilizerType(fertilizerType)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<FertilizerRecommendationDto> getRecommendationsByCropAndType(Long cropId, String fertilizerType) {
        return fertilizerRepository.findByCropIdAndFertilizerType(cropId, fertilizerType)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private FertilizerRecommendationDto toDto(FertilizerRecommendation rec) {
        return new FertilizerRecommendationDto(
                rec.getId(),
                rec.getCrop().getId(),
                rec.getCrop().getName(),
                rec.getFertilizerName(),
                rec.getFertilizerType(),
                rec.getDosagePerHectare(),
                rec.getApplicationStage(),
                rec.getApplicationMethod(),
                rec.getNotes());
    }
}
