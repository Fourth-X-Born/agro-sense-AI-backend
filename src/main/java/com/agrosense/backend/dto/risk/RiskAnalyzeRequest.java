package com.agrosense.backend.dto.risk;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskAnalyzeRequest {
    private Long farmerId; // overwritten server-side from the authenticated JWT; client value is ignored
    private Long cropId;
    private Long districtId;
}
