package com.agrosense.backend.repository;

import com.agrosense.backend.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    // Registration checks
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    // Login queries
    Optional<Farmer> findByEmail(String email);
    Optional<Farmer> findByPhone(String phone);
}
