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
        String identifier = request.getIdentifier();
        if (identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Identifier is required");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        Optional<Farmer> farmerOpt;
        // Simple check to distinguish email from phone (can be improved)
        if (identifier.contains("@")) {
            farmerOpt = farmerRepository.findByEmail(identifier);
        } else {
            farmerOpt = farmerRepository.findByPhone(identifier);
        }

        if (!farmerOpt.isPresent()) {
            throw new ResourceNotFoundException("User not found with identifier: " + identifier);
        }

        Farmer farmer = farmerOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), farmer.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid password");
        }

        String districtName = (farmer.getDistrict() != null) ? farmer.getDistrict().getName() : null;
        Long districtIdValue = (farmer.getDistrict() != null) ? farmer.getDistrict().getId() : null;
        String cropName = (farmer.getCrop() != null) ? farmer.getCrop().getName() : null;
        Long cropIdValue = (farmer.getCrop() != null) ? farmer.getCrop().getId() : null;

        // Return LoginResponse with districtId and cropId for frontend use
        return new LoginResponse(
                farmer.getId(),
                farmer.getName(),
                farmer.getEmail(),
                farmer.getPhone(),
                districtIdValue,
                districtName,
                cropIdValue,
                cropName,
                null // Token logic not implemented yet
        );
    }
}
