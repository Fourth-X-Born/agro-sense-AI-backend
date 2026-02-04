package com.agrosense.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.ProfileResponse;
import com.agrosense.backend.dto.ProfileUpdateRequest;
import com.agrosense.backend.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{farmerId}")
    public ApiResponse<ProfileResponse> getProfile(@PathVariable Long farmerId) {
        return new ApiResponse<>(true, "Profile fetched", profileService.getProfile(farmerId));
    }

    @PutMapping("/{farmerId}")
    public ApiResponse<ProfileResponse> updateProfile(
            @PathVariable Long farmerId,
            @RequestBody ProfileUpdateRequest request
    ) {
        return new ApiResponse<>(true, "Profile updated", profileService.updateProfile(farmerId, request));
    }
}
