package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.admin.*;
import com.agrosense.backend.entity.*;
import com.agrosense.backend.repository.*;
import com.agrosense.backend.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final CropRepository cropRepository;
    private final DistrictRepository districtRepository;
    private final MarketPriceRepository marketPriceRepository;
    private final FertilizerRecommendationRepository fertilizerRepository;
    private final CropDetailsRepository cropDetailsRepository;
    private final GrowthStageRepository growthStageRepository;
    private final CropGuidelineRepository cropGuidelineRepository;
    private final FarmerRepository farmerRepository;

    // ==================== CROP OPERATIONS ====================

    @Override
    public Crop createCrop(AdminCropRequest request) {
        Crop crop = Crop.builder()
                .name(request.getName())
                .build();
        return cropRepository.save(crop);
    }

    @Override
    public Crop updateCrop(Long id, AdminCropRequest request) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Crop not found with id: " + id));
        crop.setName(request.getName());
        return cropRepository.save(crop);
    }

    @Override
    public void deleteCrop(Long id) {
        if (!cropRepository.existsById(id)) {
            throw new EntityNotFoundException("Crop not found with id: " + id);
        }
        cropRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    // ==================== DISTRICT OPERATIONS ====================

    @Override
    public District createDistrict(AdminDistrictRequest request) {
        District district = District.builder()
                .name(request.getName())
                .build();
        return districtRepository.save(district);
    }

    @Override
    public District updateDistrict(Long id, AdminDistrictRequest request) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("District not found with id: " + id));
        district.setName(request.getName());
        return districtRepository.save(district);
    }

    @Override
    public void deleteDistrict(Long id) {
        if (!districtRepository.existsById(id)) {
            throw new EntityNotFoundException("District not found with id: " + id);
        }
        districtRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    // ==================== MARKET PRICE OPERATIONS ====================

    @Override
    public MarketPrice createMarketPrice(AdminMarketPriceRequest request) {
        Crop crop = cropRepository.findById(request.getCropId())
                .orElseThrow(() -> new EntityNotFoundException("Crop not found with id: " + request.getCropId()));
        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(
                        () -> new EntityNotFoundException("District not found with id: " + request.getDistrictId()));

        MarketPrice marketPrice = MarketPrice.builder()
                .crop(crop)
                .district(district)
                .pricePerKg(request.getPricePerKg())
                .priceDate(request.getPriceDate())
                .build();
        return marketPriceRepository.save(marketPrice);
    }

    @Override
    public MarketPrice updateMarketPrice(Long id, AdminMarketPriceRequest request) {
        MarketPrice marketPrice = marketPriceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Market price not found with id: " + id));

        Crop crop = cropRepository.findById(request.getCropId())
                .orElseThrow(() -> new EntityNotFoundException("Crop not found with id: " + request.getCropId()));
        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(
                        () -> new EntityNotFoundException("District not found with id: " + request.getDistrictId()));

        marketPrice.setCrop(crop);
        marketPrice.setDistrict(district);
        marketPrice.setPricePerKg(request.getPricePerKg());
        marketPrice.setPriceDate(request.getPriceDate());
        return marketPriceRepository.save(marketPrice);
    }

    @Override
    public void deleteMarketPrice(Long id) {
        if (!marketPriceRepository.existsById(id)) {
            throw new EntityNotFoundException("Market price not found with id: " + id);
        }
        marketPriceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MarketPrice> getAllMarketPrices() {
        return marketPriceRepository.findAll();
    }

    // ==================== FERTILIZER OPERATIONS ====================

    @Override
    public FertilizerRecommendation createFertilizer(AdminFertilizerRequest request) {
        Crop crop = cropRepository.findById(request.getCropId())
                .orElseThrow(() -> new EntityNotFoundException("Crop not found with id: " + request.getCropId()));

        FertilizerRecommendation fertilizer = FertilizerRecommendation.builder()
                .crop(crop)
                .fertilizerName(request.getFertilizerName())
                .fertilizerType(request.getFertilizerType())
                .dosagePerHectare(request.getDosagePerHectare())
                .applicationStage(request.getApplicationStage())
                .applicationMethod(request.getApplicationMethod())
                .notes(request.getNotes())
                .build();
        return fertilizerRepository.save(fertilizer);
    }

    @Override
    public FertilizerRecommendation updateFertilizer(Long id, AdminFertilizerRequest request) {
        FertilizerRecommendation fertilizer = fertilizerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fertilizer not found with id: " + id));

        Crop crop = cropRepository.findById(request.getCropId())
                .orElseThrow(() -> new EntityNotFoundException("Crop not found with id: " + request.getCropId()));

        fertilizer.setCrop(crop);
        fertilizer.setFertilizerName(request.getFertilizerName());
        fertilizer.setFertilizerType(request.getFertilizerType());
        fertilizer.setDosagePerHectare(request.getDosagePerHectare());
        fertilizer.setApplicationStage(request.getApplicationStage());
        fertilizer.setApplicationMethod(request.getApplicationMethod());
        fertilizer.setNotes(request.getNotes());
        return fertilizerRepository.save(fertilizer);
    }

    @Override
    public void deleteFertilizer(Long id) {
        if (!fertilizerRepository.existsById(id)) {
            throw new EntityNotFoundException("Fertilizer not found with id: " + id);
        }
        fertilizerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FertilizerRecommendation> getAllFertilizers() {
        return fertilizerRepository.findAll();
    }

    // ==================== CROP DETAILS OPERATIONS ====================

    @Override
    public CropDetails createCropDetails(AdminCropDetailsRequest request) {
        Crop crop = cropRepository.findById(request.getCropId())
                .orElseThrow(() -> new EntityNotFoundException("Crop not found with id: " + request.getCropId()));

        CropDetails details = CropDetails.builder()
                .crop(crop)
                .description(request.getDescription())
                .seasonType(request.getSeasonType())
                .growthDurationDays(request.getGrowthDurationDays())
                .optimalTemperature(request.getOptimalTemperature())
                .waterRequirement(request.getWaterRequirement())
                .soilPH(request.getSoilPH())
                .imageUrl(request.getImageUrl())
                .build();
        return cropDetailsRepository.save(details);
    }

    @Override
    public CropDetails updateCropDetails(Long id, AdminCropDetailsRequest request) {
        CropDetails details = cropDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CropDetails not found with id: " + id));

        details.setDescription(request.getDescription());
        details.setSeasonType(request.getSeasonType());
        details.setGrowthDurationDays(request.getGrowthDurationDays());
        details.setOptimalTemperature(request.getOptimalTemperature());
        details.setWaterRequirement(request.getWaterRequirement());
        details.setSoilPH(request.getSoilPH());
        details.setImageUrl(request.getImageUrl());
        return cropDetailsRepository.save(details);
    }

    @Override
    public void deleteCropDetails(Long id) {
        if (!cropDetailsRepository.existsById(id)) {
            throw new EntityNotFoundException("CropDetails not found with id: " + id);
        }
        cropDetailsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CropDetails> getAllCropDetails() {
        return cropDetailsRepository.findAll();
    }

    // ==================== GROWTH STAGE OPERATIONS ====================

    @Override
    public GrowthStage createGrowthStage(AdminGrowthStageRequest request) {
        Crop crop = cropRepository.findById(request.getCropId())
                .orElseThrow(() -> new EntityNotFoundException("Crop not found with id: " + request.getCropId()));

        GrowthStage stage = GrowthStage.builder()
                .crop(crop)
                .stageOrder(request.getStageOrder())
                .stageName(request.getStageName())
                .startDay(request.getStartDay())
                .endDay(request.getEndDay())
                .focusArea(request.getFocusArea())
                .description(request.getDescription())
                .build();
        return growthStageRepository.save(stage);
    }

    @Override
    public GrowthStage updateGrowthStage(Long id, AdminGrowthStageRequest request) {
        GrowthStage stage = growthStageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GrowthStage not found with id: " + id));

        Crop crop = cropRepository.findById(request.getCropId())
                .orElseThrow(() -> new EntityNotFoundException("Crop not found with id: " + request.getCropId()));

        stage.setCrop(crop);
        stage.setStageOrder(request.getStageOrder());
        stage.setStageName(request.getStageName());
        stage.setStartDay(request.getStartDay());
        stage.setEndDay(request.getEndDay());
        stage.setFocusArea(request.getFocusArea());
        stage.setDescription(request.getDescription());
        return growthStageRepository.save(stage);
    }

    @Override
    public void deleteGrowthStage(Long id) {
        if (!growthStageRepository.existsById(id)) {
            throw new EntityNotFoundException("GrowthStage not found with id: " + id);
        }
        growthStageRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GrowthStage> getAllGrowthStages() {
        return growthStageRepository.findAll();
    }

    // ==================== CROP GUIDELINE OPERATIONS ====================

    @Override
    public CropGuideline createCropGuideline(AdminCropGuidelineRequest request) {
        Crop crop = cropRepository.findById(request.getCropId())
                .orElseThrow(() -> new EntityNotFoundException("Crop not found with id: " + request.getCropId()));

        GrowthStage stage = null;
        if (request.getStageId() != null) {
            stage = growthStageRepository.findById(request.getStageId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "GrowthStage not found with id: " + request.getStageId()));
        }

        CropGuideline guideline = CropGuideline.builder()
                .crop(crop)
                .stage(stage)
                .guidelineType(request.getGuidelineType())
                .description(request.getDescription())
                .priority(request.getPriority())
                .build();
        return cropGuidelineRepository.save(guideline);
    }

    @Override
    public CropGuideline updateCropGuideline(Long id, AdminCropGuidelineRequest request) {
        CropGuideline guideline = cropGuidelineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CropGuideline not found with id: " + id));

        Crop crop = cropRepository.findById(request.getCropId())
                .orElseThrow(() -> new EntityNotFoundException("Crop not found with id: " + request.getCropId()));

        GrowthStage stage = null;
        if (request.getStageId() != null) {
            stage = growthStageRepository.findById(request.getStageId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "GrowthStage not found with id: " + request.getStageId()));
        }

        guideline.setCrop(crop);
        guideline.setStage(stage);
        guideline.setGuidelineType(request.getGuidelineType());
        guideline.setDescription(request.getDescription());
        guideline.setPriority(request.getPriority());
        return cropGuidelineRepository.save(guideline);
    }

    @Override
    public void deleteCropGuideline(Long id) {
        if (!cropGuidelineRepository.existsById(id)) {
            throw new EntityNotFoundException("CropGuideline not found with id: " + id);
        }
        cropGuidelineRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CropGuideline> getAllCropGuidelines() {
        return cropGuidelineRepository.findAll();
    }

    // ==================== FARMER OPERATIONS (READ-ONLY) ====================

    @Override
    @Transactional(readOnly = true)
    public List<FarmerResponse> getAllFarmers() {
        return farmerRepository.findAllWithValidRelations().stream()
                .map(FarmerResponse::fromEntity)
                .toList();
    }
}
