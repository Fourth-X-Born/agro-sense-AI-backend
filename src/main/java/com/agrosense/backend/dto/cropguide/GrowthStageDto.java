package com.agrosense.backend.dto.cropguide;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrowthStageDto {
    private Long id;
    private Integer stageOrder;
    private String stageName;
    private Integer startDay;
    private Integer endDay;
    private String focusArea;
    private String description;
}
