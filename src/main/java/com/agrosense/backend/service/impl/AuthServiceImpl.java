package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.RegisterRequest;
import com.agrosense.backend.dto.RegisterResponse;
import com.agrosense.backend.entity.Farmer;
import com.agrosense.backend.exception.DuplicateResourceException;
import com.agrosense.backend.exception.ResourceNotFoundException;
import com.agrosense.backend.entity.District;
import com.agrosense.backend.repository.FarmerRepository;
import com.agrosense.backend.repository.DistrictRepository;
import com.agrosense.backend.service.AuthService;
import com.agrosense.backend.dto.LoginRequest;
import com.agrosense.backend.dto.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private DistrictRepository districtRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (request.getDistrictId() == null) {
            throw new IllegalArgumentException("District ID is required");
        }

        Optional<District> districtOpt = districtRepository.findById(request.getDistrictId());
        if (!districtOpt.isPresent()) {
            throw new ResourceNotFoundException("District with ID " + request.getDistrictId() + " not found");
        }

        if (request.getEmail() != null && farmerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }
        if (request.getPhone() != null && farmerRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone number already registered");
        }

        String passwordHash = passwordEncoder.encode(request.getPassword());

        Farmer farmer = Farmer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .passwordHash(passwordHash)
                .district(districtOpt.get())
                .build();

        Farmer savedFarmer = farmerRepository.save(farmer);

        return new RegisterResponse(savedFarmer.getId(), savedFarmer.getName());
    }


    @Override
    public LoginResponse login(LoginRequest request) {

        String identifier = request.getIdentifier().trim();

        Farmer farmer = farmerRepository.findByEmail(identifier)
                .or(() -> farmerRepository.findByPhone(identifier))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), farmer.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new LoginResponse(
                farmer.getId(),
                farmer.getName(),
                farmer.getDistrict().getName(),
                farmer.getCrop() != null ? farmer.getCrop().getName() : null
        );
    }
}



