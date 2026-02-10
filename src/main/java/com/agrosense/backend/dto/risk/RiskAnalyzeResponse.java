package com.agrosense.backend.dto.risk;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskAnalyzeResponse {
    private Long historyId;

    private Long cropId;
    private String cropName;

    private Long districtId;
    private String districtName;

    private int riskScore; // 0–100
    private String riskLevel; // LOW/MEDIUM/HIGH

    private double avgTempC;
    private double totalRainMmNext7Days;

    private List<String> explanation;
    private List<String> recommendations;
}
