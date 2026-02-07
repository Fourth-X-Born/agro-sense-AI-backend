package com.agrosense.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "risk_analysis_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskAnalysisHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // If you don’t have Farmer table yet, store farmerId as Long only
    @Column(nullable = true)
    private Long farmerId;

    @Column(nullable = false)
    private Long cropId;

    @Column(nullable = false)
    private Long districtId;

    @Column(nullable = false)
    private String cropName;

    @Column(nullable = false)
    private String districtName;

    // Weather snapshot (for demo credibility)
    @Column(nullable = false)
    private Double avgTempC;

    @Column(nullable = false)
    private Double totalRainMmNext7Days;

    @Column(nullable = false)
    private String riskLevel; // LOW/MEDIUM/HIGH

    @Column(nullable = false)
    private Integer riskScore; // 0 - 100

    @Column(columnDefinition = "TEXT", nullable = false)
    private String explanation; // stored as text

    @Column(columnDefinition = "TEXT", nullable = false)
    private String recommendations; // stored as text

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
