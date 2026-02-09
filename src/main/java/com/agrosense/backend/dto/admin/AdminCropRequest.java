package com.agrosense.backend.dto.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCropRequest {

    @NotBlank(message = "Crop name is required")
    private String name;
}
