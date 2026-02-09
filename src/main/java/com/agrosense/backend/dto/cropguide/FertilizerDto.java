package com.agrosense.backend.dto.cropguide;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FertilizerDto {
    private String fertilizerName;
    private String dosagePerHectare;
    private String applicationStage;
}
