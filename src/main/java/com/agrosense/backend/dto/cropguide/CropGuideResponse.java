package com.agrosense.backend.dto.cropguide;

import com.agrosense.backend.dto.CropDto;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropGuideResponse {
    private CropDto crop;
    private CropDetailsDto details;
    private List<GrowthStageDto> stages;
    private GuidelinesDto guidelines;
    private List<FertilizerDto> fertilizers;
}
