package com.agrosense.backend.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCropGuidelineRequest {

    @NotNull(message = "Crop ID is required")
    private Long cropId;

    private Long stageId; // Optional - null means applies to all stages

    @NotBlank(message = "Guideline type is required (DO or DONT)")
    private String guidelineType; // "DO" or "DONT"

    @NotBlank(message = "Description is required")
    private String description;

    private Integer priority;
}
