package com.agrosense.backend.dto.cropguide;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropDetailsDto {
    private String description;
    private String seasonType;
    private Integer growthDurationDays;
    private String optimalTemperature;
    private String waterRequirement;
    private String soilPH;
    private String imageUrl;
}
