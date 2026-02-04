package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.RegisterRequest;
import com.agrosense.backend.dto.RegisterResponse;
import com.agrosense.backend.entity.Farmer;
import com.agrosense.backend.entity.District;
import com.agrosense.backend.repository.FarmerRepository;
import com.agrosense.backend.repository.DistrictRepository;
import com.agrosense.backend.service.AuthService;

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

        Optional<District> districtOpt = districtRepository.findById(request.getDistrictId());
        if (!districtOpt.isPresent()) {
            throw new IllegalArgumentException("District not found");
        }

        if (request.getEmail() != null && farmerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (request.getPhone() != null && farmerRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("Phone already exists");
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
}
