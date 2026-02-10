package com.agrosense.backend.dto.risk;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskHistoryItemDto {
    private Long id;
    private Long farmerId;
    private Long cropId;
    private String cropName;
    private Long districtId;
    private String districtName;
    private int riskScore;
    private String riskLevel;
    private double avgTempC;
    private double totalRainMmNext7Days;
    private LocalDateTime createdAt;
}
