package com.agrosense.backend.service;

import com.agrosense.backend.dto.LoginRequest;
import com.agrosense.backend.dto.LoginResponse;
import com.agrosense.backend.dto.RegisterRequest;
import com.agrosense.backend.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);

    com.agrosense.backend.dto.LoginResponse login(com.agrosense.backend.dto.LoginRequest request);
}
