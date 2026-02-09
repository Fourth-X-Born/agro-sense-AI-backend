package com.agrosense.backend.dto.admin;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminMarketPriceRequest {

    @NotNull(message = "Crop ID is required")
    private Long cropId;

    @NotNull(message = "District ID is required")
    private Long districtId;

    @NotNull(message = "Price per kg is required")
    private BigDecimal pricePerKg;

    @NotNull(message = "Price date is required")
    private LocalDate priceDate;
}
