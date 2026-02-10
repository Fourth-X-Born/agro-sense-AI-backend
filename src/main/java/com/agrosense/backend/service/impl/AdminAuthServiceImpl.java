package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.admin.AdminLoginRequest;
import com.agrosense.backend.dto.admin.AdminLoginResponse;
import com.agrosense.backend.dto.admin.AdminRegisterRequest;
import com.agrosense.backend.dto.admin.AdminRegisterResponse;
import com.agrosense.backend.entity.Admin;
import com.agrosense.backend.exception.DuplicateResourceException;
import com.agrosense.backend.exception.ResourceNotFoundException;
import com.agrosense.backend.repository.AdminRepository;
import com.agrosense.backend.service.AdminAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    @Autowired
    private AdminRepository adminRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AdminRegisterResponse register(AdminRegisterRequest request) {
        // Validate required fields
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (request.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        // Check for duplicate email
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }

        // Check for duplicate phone if provided
        if (request.getPhone() != null && !request.getPhone().isEmpty() 
            && adminRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone number already registered");
        }

        // Hash password
        String passwordHash = passwordEncoder.encode(request.getPassword());

        // Set default role if not provided
        String role = (request.getRole() != null && !request.getRole().isEmpty()) 
            ? request.getRole() 
            : "ADMIN";

        // Create admin entity
        Admin admin = Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .passwordHash(passwordHash)
                .role(role)
                .build();

        Admin savedAdmin = adminRepository.save(admin);

        return AdminRegisterResponse.builder()
                .id(savedAdmin.getId())
                .name(savedAdmin.getName())
                .email(savedAdmin.getEmail())
                .role(savedAdmin.getRole())
                .message("Admin registered successfully")
                .build();
    }

    @Override
    public AdminLoginResponse login(AdminLoginRequest request) {
        // Validate required fields
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        // Find admin by email
        Optional<Admin> adminOpt = adminRepository.findByEmail(request.getEmail());
        
        if (!adminOpt.isPresent()) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        Admin admin = adminOpt.get();

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), admin.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Return login response
        return AdminLoginResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .role(admin.getRole())
                .token("admin-session-" + admin.getId()) // Simple token for now
                .build();
    }
}
