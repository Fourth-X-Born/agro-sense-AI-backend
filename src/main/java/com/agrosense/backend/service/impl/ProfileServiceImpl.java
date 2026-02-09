package com.agrosense.backend.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.agrosense.backend.dto.ProfileResponse;
import com.agrosense.backend.dto.ProfileUpdateRequest;
import com.agrosense.backend.entity.Crop;
import com.agrosense.backend.entity.District;
import com.agrosense.backend.entity.Farmer;
import com.agrosense.backend.repository.CropRepository;
import com.agrosense.backend.repository.DistrictRepository;
import com.agrosense.backend.repository.FarmerRepository;
import com.agrosense.backend.service.CloudinaryService;
import com.agrosense.backend.service.ProfileService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final FarmerRepository farmerRepository;
    private final DistrictRepository districtRepository;
    private final CropRepository cropRepository;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;

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

        // Update name if provided
        if (request.name() != null && !request.name().isBlank()) {
            farmer.setName(request.name());
        }

        // Update phone if provided
        if (request.phone() != null && !request.phone().isBlank()) {
            farmer.setPhone(request.phone());
        }

        // Update district if provided
        if (request.districtId() != null) {
            District district = districtRepository.findById(request.districtId())
                    .orElseThrow(() -> new RuntimeException("District not found: " + request.districtId()));
            farmer.setDistrict(district);
        }

        // Update crop (can be null to remove crop)
        if (request.cropId() != null) {
            Crop crop = cropRepository.findById(request.cropId())
                    .orElseThrow(() -> new RuntimeException("Crop not found: " + request.cropId()));
            farmer.setCrop(crop);
        }

        Farmer saved = farmerRepository.save(farmer);
        return toResponse(saved);
    }

    @Override
    public ProfileResponse uploadProfilePhoto(Long farmerId, MultipartFile file) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found: " + farmerId));

        try {
            // Delete old photo from Cloudinary if exists
            if (farmer.getProfilePhoto() != null) {
                String publicId = cloudinaryService.extractPublicId(farmer.getProfilePhoto());
                if (publicId != null) {
                    cloudinaryService.deleteImage(publicId);
                }
            }

            // Upload new photo to Cloudinary
            String publicId = "farmer_" + farmerId;
            String imageUrl = cloudinaryService.uploadImage(file, "agrosense/profile-photos", publicId);

            // Update farmer with Cloudinary URL
            farmer.setProfilePhoto(imageUrl);
            Farmer saved = farmerRepository.save(farmer);
            return toResponse(saved);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload photo: " + e.getMessage());
        }
    }

    @Override
    public ProfileResponse deleteProfilePhoto(Long farmerId) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found: " + farmerId));

        if (farmer.getProfilePhoto() != null) {
            try {
                String publicId = cloudinaryService.extractPublicId(farmer.getProfilePhoto());
                if (publicId != null) {
                    cloudinaryService.deleteImage(publicId);
                }
            } catch (IOException e) {
                System.err.println("Failed to delete photo from Cloudinary: " + e.getMessage());
            }
            farmer.setProfilePhoto(null);
            farmerRepository.save(farmer);
        }

        return toResponse(farmer);
    }

    @Override
    public void changePassword(Long farmerId, String currentPassword, String newPassword) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found: " + farmerId));

        // Verify current password
        if (!passwordEncoder.matches(currentPassword, farmer.getPasswordHash())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        // Validate new password
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("New password must be at least 6 characters");
        }

        // Update password
        farmer.setPasswordHash(passwordEncoder.encode(newPassword));
        farmerRepository.save(farmer);
    }

    private ProfileResponse toResponse(Farmer farmer) {
        Long cropId = farmer.getCrop() != null ? farmer.getCrop().getId() : null;
        String cropName = farmer.getCrop() != null ? farmer.getCrop().getName() : null;
        Long districtId = farmer.getDistrict() != null ? farmer.getDistrict().getId() : null;
        String districtName = farmer.getDistrict() != null ? farmer.getDistrict().getName() : null;

        return new ProfileResponse(
                farmer.getId(),
                farmer.getName(),
                farmer.getEmail(),
                farmer.getPhone(),
                districtId,
                districtName,
                cropId,
                cropName,
                farmer.getProfilePhoto());
    }
}
