package com.agrosense.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrosense.backend.entity.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}