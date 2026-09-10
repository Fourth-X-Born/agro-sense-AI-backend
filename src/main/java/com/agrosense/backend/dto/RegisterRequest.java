package com.agrosense.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Email(message = "Enter a valid email address")
    private String email; // optional (can login with phone instead)

    @Pattern(
        regexp = "^(?:\\+94|94|0)[1-9]\\d{8}$",
        message = "Invalid phone number. Use format: 07XXXXXXXX or +94XXXXXXXXX"
    )
    private String phone; // optional

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d).+$",
        message = "Password must contain at least one uppercase letter and one number"
    )
    private String password;

    private Long districtId;
}
