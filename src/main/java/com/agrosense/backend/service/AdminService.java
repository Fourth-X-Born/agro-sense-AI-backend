package com.agrosense.backend.service;

import com.agrosense.backend.dto.admin.*;
import com.agrosense.backend.entity.*;
import java.util.List;

public interface AdminService {

    // Crop operations
    Crop createCrop(AdminCropRequest request);

    Crop updateCrop(Long id, AdminCropRequest request);

    void deleteCrop(Long id);

    List<Crop> getAllCrops();

    // District operations
    District createDistrict(AdminDistrictRequest request);

    District updateDistrict(Long id, AdminDistrictRequest request);

    void deleteDistrict(Long id);

    List<District> getAllDistricts();

    // Market Price operations
    MarketPrice createMarketPrice(AdminMarketPriceRequest request);

    MarketPrice updateMarketPrice(Long id, AdminMarketPriceRequest request);

    void deleteMarketPrice(Long id);

    List<MarketPrice> getAllMarketPrices();

    // Fertilizer Recommendation operations
    FertilizerRecommendation createFertilizer(AdminFertilizerRequest request);

    FertilizerRecommendation updateFertilizer(Long id, AdminFertilizerRequest request);

    void deleteFertilizer(Long id);

    List<FertilizerRecommendation> getAllFertilizers();

    // CropDetails operations
    CropDetails createCropDetails(AdminCropDetailsRequest request);

    CropDetails updateCropDetails(Long id, AdminCropDetailsRequest request);

    void deleteCropDetails(Long id);

    List<CropDetails> getAllCropDetails();

    // GrowthStage operations
    GrowthStage createGrowthStage(AdminGrowthStageRequest request);

    GrowthStage updateGrowthStage(Long id, AdminGrowthStageRequest request);

    void deleteGrowthStage(Long id);

    List<GrowthStage> getAllGrowthStages();

    // CropGuideline operations
    CropGuideline createCropGuideline(AdminCropGuidelineRequest request);

    CropGuideline updateCropGuideline(Long id, AdminCropGuidelineRequest request);

    void deleteCropGuideline(Long id);

    List<CropGuideline> getAllCropGuidelines();
}
