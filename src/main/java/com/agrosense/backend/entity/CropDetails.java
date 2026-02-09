package com.agrosense.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "crop_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropDetails {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "crop_id")
    private Crop crop;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "season_type")
    private String seasonType; // "Dry", "Wet", "Both"

    @Column(name = "growth_duration_days")
    private Integer growthDurationDays;

    @Column(name = "optimal_temperature")
    private String optimalTemperature; // "25-32°C"

    @Column(name = "water_requirement")
    private String waterRequirement; // "5-10 cm depth"

    @Column(name = "soil_ph")
    private String soilPH; // "6.0-7.0"

    @Column(name = "image_url")
    private String imageUrl;
}
