package com.agrosense.backend.dto;

public record ProfileUpdateRequest(
                String name,
                Long districtId,
                Long cropId) {
}
