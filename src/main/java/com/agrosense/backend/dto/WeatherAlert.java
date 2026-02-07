package com.agrosense.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherAlert {
    private String alertType;
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL
    private String message;
    private List<String> precautions;
}
