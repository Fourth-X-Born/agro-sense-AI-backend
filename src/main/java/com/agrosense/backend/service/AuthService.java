package com.agrosense.backend.service;

import com.agrosense.backend.dto.LoginRequest;
import com.agrosense.backend.dto.LoginResponse;
import com.agrosense.backend.dto.RegisterRequest;
import com.agrosense.backend.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
