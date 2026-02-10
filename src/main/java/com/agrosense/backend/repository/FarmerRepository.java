package com.agrosense.backend.repository;

import com.agrosense.backend.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    // Registration checks
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    // Login queries
    Optional<Farmer> findByEmail(String email);

    Optional<Farmer> findByPhone(String phone);

    // Native query for admin - returns farmers with valid district references only
    @Query(value = "SELECT f.* FROM farmers f " +
            "INNER JOIN districts d ON f.district_id = d.id " +
            "LEFT JOIN crops c ON f.crop_id = c.id", nativeQuery = true)
    List<Farmer> findAllWithValidRelations();
}
