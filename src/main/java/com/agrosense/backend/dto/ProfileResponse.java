package com.agrosense.backend.dto;

public record ProfileResponse(
        Long farmerId,
        String name,
        String email,
        String phone,
        Long districtId,
        String districtName,
        Long cropId,
        String cropName,
        String profilePhoto
) {}
