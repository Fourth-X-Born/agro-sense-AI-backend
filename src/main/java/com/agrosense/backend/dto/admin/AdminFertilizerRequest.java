package com.agrosense.backend.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminFertilizerRequest {

    @NotNull(message = "Crop ID is required")
    private Long cropId;

    @NotBlank(message = "Fertilizer name is required")
    private String fertilizerName;

    @NotBlank(message = "Fertilizer type is required")
    private String fertilizerType;

    @NotBlank(message = "Dosage per hectare is required")
    private String dosagePerHectare;

    @NotBlank(message = "Application stage is required")
    private String applicationStage;

    private String applicationMethod;

    private String notes;
}
