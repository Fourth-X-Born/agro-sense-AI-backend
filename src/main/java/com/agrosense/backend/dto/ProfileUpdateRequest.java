package com.agrosense.backend.dto;

public record ProfileUpdateRequest(
        Long districtId,
        Long cropId
) {}
