package com.agrosense.backend.service;

import com.agrosense.backend.dto.ProfileResponse;
import com.agrosense.backend.dto.ProfileUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    ProfileResponse getProfile(Long farmerId);
    ProfileResponse updateProfile(Long farmerId, ProfileUpdateRequest request);
    ProfileResponse uploadProfilePhoto(Long farmerId, MultipartFile file);
    ProfileResponse deleteProfilePhoto(Long farmerId);
    void changePassword(Long farmerId, String currentPassword, String newPassword);
}
