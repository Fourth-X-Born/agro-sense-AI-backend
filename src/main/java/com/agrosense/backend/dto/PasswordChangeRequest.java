package com.agrosense.backend.dto;

public record PasswordChangeRequest(
    String currentPassword,
    String newPassword
) {}
