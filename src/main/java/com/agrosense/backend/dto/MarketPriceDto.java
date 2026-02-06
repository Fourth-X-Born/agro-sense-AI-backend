package com.agrosense.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MarketPriceDto(
        Long id,
        Long cropId,
        String cropName,
        Long districtId,
        String districtName,
        BigDecimal pricePerKg,
        LocalDate priceDate) {
}
