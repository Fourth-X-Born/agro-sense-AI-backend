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
}
