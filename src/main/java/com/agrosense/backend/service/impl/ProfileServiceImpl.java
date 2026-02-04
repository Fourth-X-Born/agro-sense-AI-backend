package com.agrosense.backend.service.impl;

import org.springframework.stereotype.Service;

import com.agrosense.backend.dto.ProfileResponse;
import com.agrosense.backend.dto.ProfileUpdateRequest;
import com.agrosense.backend.entity.Crop;
import com.agrosense.backend.entity.District;
import com.agrosense.backend.entity.Farmer;
import com.agrosense.backend.repository.CropRepository;
import com.agrosense.backend.repository.DistrictRepository;
import com.agrosense.backend.repository.FarmerRepository;
import com.agrosense.backend.service.ProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final FarmerRepository farmerRepository;
    private final DistrictRepository districtRepository;
    private final CropRepository cropRepository;

    @Override
    public ProfileResponse getProfile(Long farmerId) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found: " + farmerId));
        return toResponse(farmer);
    }

    @Override
    public ProfileResponse updateProfile(Long farmerId, ProfileUpdateRequest request) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found: " + farmerId));

        District district = districtRepository.findById(request.districtId())
                .orElseThrow(() -> new RuntimeException("District not found: " + request.districtId()));
        farmer.setDistrict(district);

        if (request.cropId() != null) {
            Crop crop = cropRepository.findById(request.cropId())
                    .orElseThrow(() -> new RuntimeException("Crop not found: " + request.cropId()));
            farmer.setCrop(crop);
        } else {
            farmer.setCrop(null);
        }

        Farmer saved = farmerRepository.save(farmer);
        return toResponse(saved);
    }

    private ProfileResponse toResponse(Farmer farmer) {
        Long cropId = farmer.getCrop() != null ? farmer.getCrop().getId() : null;
        String cropName = farmer.getCrop() != null ? farmer.getCrop().getName() : null;

        return new ProfileResponse(
                farmer.getId(),
                farmer.getName(),
                farmer.getEmail(),
                farmer.getPhone(),
                farmer.getDistrict().getId(),
                farmer.getDistrict().getName(),
                cropId,
                cropName
        );
    }
}
