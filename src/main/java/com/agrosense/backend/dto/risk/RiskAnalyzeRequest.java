package com.agrosense.backend.dto.risk;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskAnalyzeRequest {
    private Long farmerId; // optional for now
    private Long cropId;
    private Long districtId;
}
