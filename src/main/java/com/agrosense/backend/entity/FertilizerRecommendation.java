package com.agrosense.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fertilizer_recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FertilizerRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;

    @Column(name = "fertilizer_name", nullable = false)
    private String fertilizerName;

    @Column(name = "fertilizer_type", nullable = false)
    private String fertilizerType; // e.g., "Nitrogen", "Phosphorus", "Potassium", "Organic"

    @Column(name = "dosage_per_hectare", nullable = false)
    private String dosagePerHectare; // e.g., "100-150 kg/ha"

    @Column(name = "application_stage", nullable = false)
    private String applicationStage; // e.g., "Before planting", "During growth", "Flowering stage"

    @Column(name = "application_method")
    private String applicationMethod; // e.g., "Broadcasting", "Foliar spray", "Side dressing"

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
