package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.CropDto;
import com.agrosense.backend.dto.cropguide.*;
import com.agrosense.backend.entity.*;
import com.agrosense.backend.repository.*;
import com.agrosense.backend.service.CropGuideService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CropGuideServiceImpl implements CropGuideService {

    private final CropRepository cropRepository;
    private final CropDetailsRepository cropDetailsRepository;
    private final GrowthStageRepository growthStageRepository;
    private final CropGuidelineRepository cropGuidelineRepository;
    private final FertilizerRecommendationRepository fertilizerRepository;

    @Override
    public CropGuideResponse getCropGuide(Long cropId) {
        // Fetch crop
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new EntityNotFoundException("Crop not found with id: " + cropId));

        // Fetch crop details
        CropDetailsDto detailsDto = cropDetailsRepository.findByCropId(cropId)
                .map(this::mapToDetailsDto)
                .orElse(null);

        // Fetch growth stages
        List<GrowthStageDto> stageDtos = growthStageRepository.findByCropIdOrderByStageOrder(cropId)
                .stream()
                .map(this::mapToStageDto)
                .collect(Collectors.toList());

        // Fetch guidelines and separate into dos and donts
        List<CropGuideline> allGuidelines = cropGuidelineRepository.findByCropIdOrderByPriority(cropId);
        List<String> dos = allGuidelines.stream()
                .filter(g -> "DO".equalsIgnoreCase(g.getGuidelineType()))
                .map(CropGuideline::getDescription)
                .collect(Collectors.toList());
        List<String> donts = allGuidelines.stream()
                .filter(g -> "DONT".equalsIgnoreCase(g.getGuidelineType()))
                .map(CropGuideline::getDescription)
                .collect(Collectors.toList());
        GuidelinesDto guidelinesDto = GuidelinesDto.builder()
                .dos(dos)
                .donts(donts)
                .build();

        // Fetch fertilizers
        List<FertilizerDto> fertilizerDtos = fertilizerRepository.findByCropId(cropId)
                .stream()
                .map(this::mapToFertilizerDto)
                .collect(Collectors.toList());

        return CropGuideResponse.builder()
                .crop(new CropDto(crop.getId(), crop.getName()))
                .details(detailsDto)
                .stages(stageDtos)
                .guidelines(guidelinesDto)
                .fertilizers(fertilizerDtos)
                .build();
    }

    private CropDetailsDto mapToDetailsDto(CropDetails details) {
        return CropDetailsDto.builder()
                .description(details.getDescription())
                .seasonType(details.getSeasonType())
                .growthDurationDays(details.getGrowthDurationDays())
                .optimalTemperature(details.getOptimalTemperature())
                .waterRequirement(details.getWaterRequirement())
                .soilPH(details.getSoilPH())
                .imageUrl(details.getImageUrl())
                .build();
    }

    private GrowthStageDto mapToStageDto(GrowthStage stage) {
        return GrowthStageDto.builder()
                .id(stage.getId())
                .stageOrder(stage.getStageOrder())
                .stageName(stage.getStageName())
                .startDay(stage.getStartDay())
                .endDay(stage.getEndDay())
                .focusArea(stage.getFocusArea())
                .description(stage.getDescription())
                .build();
    }

    private FertilizerDto mapToFertilizerDto(FertilizerRecommendation fertilizer) {
        return FertilizerDto.builder()
                .fertilizerName(fertilizer.getFertilizerName())
                .dosagePerHectare(fertilizer.getDosagePerHectare())
                .applicationStage(fertilizer.getApplicationStage())
                .build();
    }
}
