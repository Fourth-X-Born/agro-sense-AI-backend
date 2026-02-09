package com.agrosense.backend.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.PasswordChangeRequest;
import com.agrosense.backend.dto.ProfileResponse;
import com.agrosense.backend.dto.ProfileUpdateRequest;
import com.agrosense.backend.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/get")
    public ApiResponse<ProfileResponse> getProfile(@RequestParam Long farmerId) {
        return new ApiResponse<>(true, "Profile fetched", profileService.getProfile(farmerId));
    }

    @PutMapping("/update")
    public ApiResponse<ProfileResponse> updateProfile(
            @RequestParam Long farmerId,
            @RequestBody ProfileUpdateRequest request) {
        return new ApiResponse<>(true, "Profile updated", profileService.updateProfile(farmerId, request));
    }

    @PostMapping("/photo/upload")
    public ApiResponse<ProfileResponse> uploadPhoto(
            @RequestParam Long farmerId,
            @RequestParam("file") MultipartFile file) {
        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return new ApiResponse<>(false, "Only image files are allowed", null);
        }
        // Validate file size (max 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            return new ApiResponse<>(false, "File size must be less than 5MB", null);
        }
        return new ApiResponse<>(true, "Photo uploaded successfully", profileService.uploadProfilePhoto(farmerId, file));
    }

    @DeleteMapping("/photo/delete")
    public ApiResponse<ProfileResponse> deletePhoto(@RequestParam Long farmerId) {
        return new ApiResponse<>(true, "Photo deleted successfully", profileService.deleteProfilePhoto(farmerId));
    }

    @PutMapping("/change-password")
    public ApiResponse<Void> changePassword(
            @RequestParam Long farmerId,
            @RequestBody PasswordChangeRequest request) {
        try {
            profileService.changePassword(farmerId, request.currentPassword(), request.newPassword());
            return new ApiResponse<>(true, "Password changed successfully", null);
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(false, e.getMessage(), null);
        }
    }
}
