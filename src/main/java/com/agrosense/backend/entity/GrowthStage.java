package com.agrosense.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "growth_stages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrowthStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;

    @Column(name = "stage_order", nullable = false)
    private Integer stageOrder; // 1, 2, 3, 4

    @Column(name = "stage_name", nullable = false)
    private String stageName; // "Seedling", "Vegetative"

    @Column(name = "start_day", nullable = false)
    private Integer startDay;

    @Column(name = "end_day", nullable = false)
    private Integer endDay;

    @Column(name = "focus_area")
    private String focusArea; // "Root development"

    @Column(columnDefinition = "TEXT")
    private String description;
}
