package com.agrosense.backend.dto.admin;

import lombok.Data;

@Data
public class AdminContactMessageUpdateRequest {
    private String status;
    private String adminNotes;
}
