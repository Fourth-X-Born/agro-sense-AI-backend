package com.agrosense.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    private String identifier; // email OR phone

    @NotBlank
    private String password;
}
