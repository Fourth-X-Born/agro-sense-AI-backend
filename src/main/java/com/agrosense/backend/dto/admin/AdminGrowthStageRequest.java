package com.agrosense.backend.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGrowthStageRequest {

    @NotNull(message = "Crop ID is required")
    private Long cropId;

    @NotNull(message = "Stage order is required")
    private Integer stageOrder;

    @NotBlank(message = "Stage name is required")
    private String stageName;

    @NotNull(message = "Start day is required")
    private Integer startDay;

    @NotNull(message = "End day is required")
    private Integer endDay;

    private String focusArea;
    private String description;
}
