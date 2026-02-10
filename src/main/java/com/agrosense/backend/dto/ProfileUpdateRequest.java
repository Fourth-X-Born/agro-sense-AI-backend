package com.agrosense.backend.dto;

public record ProfileUpdateRequest(
                String name,
                String phone,
                Long districtId,
                Long cropId) {
}
