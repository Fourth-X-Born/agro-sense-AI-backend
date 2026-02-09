package com.agrosense.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "crop_guidelines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropGuideline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    private GrowthStage stage; // Optional - null means applies to all stages

    @Column(name = "guideline_type", nullable = false)
    private String guidelineType; // "DO" or "DONT"

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column
    private Integer priority; // Display order
}
