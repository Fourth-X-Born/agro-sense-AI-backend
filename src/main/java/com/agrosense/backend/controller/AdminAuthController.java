package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.admin.AdminLoginRequest;
import com.agrosense.backend.dto.admin.AdminLoginResponse;
import com.agrosense.backend.dto.admin.AdminRegisterRequest;
import com.agrosense.backend.dto.admin.AdminRegisterResponse;
import com.agrosense.backend.service.AdminAuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    @Autowired
    private AdminAuthService adminAuthService;

    @PostMapping("/register")
    public ApiResponse<AdminRegisterResponse> register(@Valid @RequestBody AdminRegisterRequest request) {
        AdminRegisterResponse response = adminAuthService.register(request);
        return new ApiResponse<>(true, "Admin registration successful", response);
    }

    @PostMapping("/login")
    public ApiResponse<AdminLoginResponse> login(@Valid @RequestBody AdminLoginRequest request) {
        AdminLoginResponse response = adminAuthService.login(request);
        return new ApiResponse<>(true, "Admin login successful", response);
    }
}
