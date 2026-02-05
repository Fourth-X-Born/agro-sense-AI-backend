package com.agrosense.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long farmerId;
    private String name;
    private String district;
    private String crop;
}

