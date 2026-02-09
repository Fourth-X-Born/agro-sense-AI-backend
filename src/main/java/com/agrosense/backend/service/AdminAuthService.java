package com.agrosense.backend.service;

import com.agrosense.backend.dto.admin.AdminLoginRequest;
import com.agrosense.backend.dto.admin.AdminLoginResponse;
import com.agrosense.backend.dto.admin.AdminRegisterRequest;
import com.agrosense.backend.dto.admin.AdminRegisterResponse;

public interface AdminAuthService {
    
    AdminRegisterResponse register(AdminRegisterRequest request);
    
    AdminLoginResponse login(AdminLoginRequest request);
}
