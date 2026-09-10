package com.agrosense.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PasswordChangeRequest {

    @NotBlank(message = "Current password is required")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "New password must be at least 8 characters")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d).+$",
        message = "New password must contain at least one uppercase letter and one number"
    )
    private String newPassword;
}
