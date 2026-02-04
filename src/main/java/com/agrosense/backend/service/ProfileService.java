package com.agrosense.backend.service;

import com.agrosense.backend.dto.ProfileResponse;
import com.agrosense.backend.dto.ProfileUpdateRequest;

public interface ProfileService {
    ProfileResponse getProfile(Long farmerId);
    ProfileResponse updateProfile(Long farmerId, ProfileUpdateRequest request);
}
