package com.agrosense.backend.controller;

import com.agrosense.backend.dto.admin.*;
import com.agrosense.backend.entity.*;
import com.agrosense.backend.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ==================== CROP ENDPOINTS ====================

    @GetMapping("/crops")
    public ResponseEntity<List<Crop>> getAllCrops() {
        return ResponseEntity.ok(adminService.getAllCrops());
    }

    @PostMapping("/crops")
    public ResponseEntity<Crop> createCrop(@Valid @RequestBody AdminCropRequest request) {
        Crop crop = adminService.createCrop(request);
        return ResponseEntity.ok(crop);
    }

    @PutMapping("/crops/{id}")
    public ResponseEntity<Crop> updateCrop(@PathVariable("id") Long id, @Valid @RequestBody AdminCropRequest request) {
        Crop crop = adminService.updateCrop(id, request);
        return ResponseEntity.ok(crop);
    }

    @DeleteMapping("/crops/{id}")
    public ResponseEntity<Map<String, String>> deleteCrop(@PathVariable("id") Long id) {
        adminService.deleteCrop(id);
        return ResponseEntity.ok(Map.of("message", "Crop deleted successfully"));
    }

    // ==================== DISTRICT ENDPOINTS ====================

    @GetMapping("/districts")
    public ResponseEntity<List<District>> getAllDistricts() {
        return ResponseEntity.ok(adminService.getAllDistricts());
    }

    @PostMapping("/districts")
    public ResponseEntity<District> createDistrict(@Valid @RequestBody AdminDistrictRequest request) {
        District district = adminService.createDistrict(request);
        return ResponseEntity.ok(district);
    }

    @PutMapping("/districts/{id}")
    public ResponseEntity<District> updateDistrict(@PathVariable("id") Long id,
            @Valid @RequestBody AdminDistrictRequest request) {
        District district = adminService.updateDistrict(id, request);
        return ResponseEntity.ok(district);
    }

    @DeleteMapping("/districts/{id}")
    public ResponseEntity<Map<String, String>> deleteDistrict(@PathVariable("id") Long id) {
        adminService.deleteDistrict(id);
        return ResponseEntity.ok(Map.of("message", "District deleted successfully"));
    }

    // ==================== MARKET PRICE ENDPOINTS ====================

    @GetMapping("/market-prices")
    public ResponseEntity<List<MarketPrice>> getAllMarketPrices() {
        return ResponseEntity.ok(adminService.getAllMarketPrices());
    }

    @PostMapping("/market-prices")
    public ResponseEntity<MarketPrice> createMarketPrice(@Valid @RequestBody AdminMarketPriceRequest request) {
        MarketPrice marketPrice = adminService.createMarketPrice(request);
        return ResponseEntity.ok(marketPrice);
    }

    @PutMapping("/market-prices/{id}")
    public ResponseEntity<MarketPrice> updateMarketPrice(@PathVariable("id") Long id,
            @Valid @RequestBody AdminMarketPriceRequest request) {
        MarketPrice marketPrice = adminService.updateMarketPrice(id, request);
        return ResponseEntity.ok(marketPrice);
    }

    @DeleteMapping("/market-prices/{id}")
    public ResponseEntity<Map<String, String>> deleteMarketPrice(@PathVariable("id") Long id) {
        adminService.deleteMarketPrice(id);
        return ResponseEntity.ok(Map.of("message", "Market price deleted successfully"));
    }

    // ==================== FERTILIZER ENDPOINTS ====================

    @GetMapping("/fertilizers")
    public ResponseEntity<List<FertilizerRecommendation>> getAllFertilizers() {
        return ResponseEntity.ok(adminService.getAllFertilizers());
    }

    @PostMapping("/fertilizers")
    public ResponseEntity<FertilizerRecommendation> createFertilizer(
            @Valid @RequestBody AdminFertilizerRequest request) {
        FertilizerRecommendation fertilizer = adminService.createFertilizer(request);
        return ResponseEntity.ok(fertilizer);
    }

    @PutMapping("/fertilizers/{id}")
    public ResponseEntity<FertilizerRecommendation> updateFertilizer(@PathVariable("id") Long id,
            @Valid @RequestBody AdminFertilizerRequest request) {
        FertilizerRecommendation fertilizer = adminService.updateFertilizer(id, request);
        return ResponseEntity.ok(fertilizer);
    }

    @DeleteMapping("/fertilizers/{id}")
    public ResponseEntity<Map<String, String>> deleteFertilizer(@PathVariable("id") Long id) {
        adminService.deleteFertilizer(id);
        return ResponseEntity.ok(Map.of("message", "Fertilizer recommendation deleted successfully"));
    }

    // ==================== CROP DETAILS ENDPOINTS ====================

    @GetMapping("/crop-details")
    public ResponseEntity<List<CropDetails>> getAllCropDetails() {
        return ResponseEntity.ok(adminService.getAllCropDetails());
    }

    @PostMapping("/crop-details")
    public ResponseEntity<CropDetails> createCropDetails(@Valid @RequestBody AdminCropDetailsRequest request) {
        CropDetails details = adminService.createCropDetails(request);
        return ResponseEntity.ok(details);
    }

    @PutMapping("/crop-details/{id}")
    public ResponseEntity<CropDetails> updateCropDetails(@PathVariable("id") Long id,
            @Valid @RequestBody AdminCropDetailsRequest request) {
        CropDetails details = adminService.updateCropDetails(id, request);
        return ResponseEntity.ok(details);
    }

    @DeleteMapping("/crop-details/{id}")
    public ResponseEntity<Map<String, String>> deleteCropDetails(@PathVariable("id") Long id) {
        adminService.deleteCropDetails(id);
        return ResponseEntity.ok(Map.of("message", "Crop details deleted successfully"));
    }

    // ==================== GROWTH STAGE ENDPOINTS ====================

    @GetMapping("/growth-stages")
    public ResponseEntity<List<GrowthStage>> getAllGrowthStages() {
        return ResponseEntity.ok(adminService.getAllGrowthStages());
    }

    @PostMapping("/growth-stages")
    public ResponseEntity<GrowthStage> createGrowthStage(@Valid @RequestBody AdminGrowthStageRequest request) {
        GrowthStage stage = adminService.createGrowthStage(request);
        return ResponseEntity.ok(stage);
    }

    @PutMapping("/growth-stages/{id}")
    public ResponseEntity<GrowthStage> updateGrowthStage(@PathVariable("id") Long id,
            @Valid @RequestBody AdminGrowthStageRequest request) {
        GrowthStage stage = adminService.updateGrowthStage(id, request);
        return ResponseEntity.ok(stage);
    }

    @DeleteMapping("/growth-stages/{id}")
    public ResponseEntity<Map<String, String>> deleteGrowthStage(@PathVariable("id") Long id) {
        adminService.deleteGrowthStage(id);
        return ResponseEntity.ok(Map.of("message", "Growth stage deleted successfully"));
    }

    // ==================== CROP GUIDELINE ENDPOINTS ====================

    @GetMapping("/crop-guidelines")
    public ResponseEntity<List<CropGuideline>> getAllCropGuidelines() {
        return ResponseEntity.ok(adminService.getAllCropGuidelines());
    }

    @PostMapping("/crop-guidelines")
    public ResponseEntity<CropGuideline> createCropGuideline(@Valid @RequestBody AdminCropGuidelineRequest request) {
        CropGuideline guideline = adminService.createCropGuideline(request);
        return ResponseEntity.ok(guideline);
    }

    @PutMapping("/crop-guidelines/{id}")
    public ResponseEntity<CropGuideline> updateCropGuideline(@PathVariable("id") Long id,
            @Valid @RequestBody AdminCropGuidelineRequest request) {
        CropGuideline guideline = adminService.updateCropGuideline(id, request);
        return ResponseEntity.ok(guideline);
    }

    @DeleteMapping("/crop-guidelines/{id}")
    public ResponseEntity<Map<String, String>> deleteCropGuideline(@PathVariable("id") Long id) {
        adminService.deleteCropGuideline(id);
        return ResponseEntity.ok(Map.of("message", "Crop guideline deleted successfully"));
    }
}
