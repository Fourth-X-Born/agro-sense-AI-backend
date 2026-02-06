package com.agrosense.backend.repository;

import com.agrosense.backend.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    java.util.Optional<Farmer> findByEmail(String email);
}
