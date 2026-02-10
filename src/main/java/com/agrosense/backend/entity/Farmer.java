package com.agrosense.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "farmers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email; // optional

    @Column(unique = true)
    private String phone; // optional

    @JsonIgnore
    @Column(nullable = false)
    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district; // optional, can be set later

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private Crop crop;

    @Column(name = "profile_photo")
    private String profilePhoto; // URL or path to profile photo
}
