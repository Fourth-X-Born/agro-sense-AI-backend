package com.agrosense.backend.dto.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDistrictRequest {

    @NotBlank(message = "District name is required")
    private String name;
}
