package com.agrosense.backend.dto.admin;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCropDetailsRequest {

    @NotNull(message = "Crop ID is required")
    private Long cropId;

    private String description;
    private String seasonType;
    private Integer growthDurationDays;
    private String optimalTemperature;
    private String waterRequirement;
    private String soilPH;
    private String imageUrl;
}
