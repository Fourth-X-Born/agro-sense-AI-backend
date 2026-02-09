package com.agrosense.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyForecast {
    private String day; // e.g., "Today", "Wed", "Thu"
    private String date; // e.g., "Feb 9"
    private String icon; // Material icon name
    private int high; // Max temperature
    private int low; // Min temperature
    private String description; // Weather description
}
