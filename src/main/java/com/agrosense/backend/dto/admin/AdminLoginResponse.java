package com.agrosense.backend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginResponse {
    
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String token; // For future JWT implementation
}
