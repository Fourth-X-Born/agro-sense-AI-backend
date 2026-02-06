package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.RegisterRequest;
import com.agrosense.backend.dto.RegisterResponse;
import com.agrosense.backend.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return new ApiResponse<>(true, "Registration successful", response);
    }

    @PostMapping("/login")
    public ApiResponse<com.agrosense.backend.dto.LoginResponse> login(
            @RequestBody com.agrosense.backend.dto.LoginRequest request) {
        com.agrosense.backend.dto.LoginResponse response = authService.login(request);
        return new ApiResponse<>(true, "Login successful", response);
    }
}
