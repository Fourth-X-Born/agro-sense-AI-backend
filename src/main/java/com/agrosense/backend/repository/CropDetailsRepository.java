package com.agrosense.backend.repository;

import com.agrosense.backend.entity.CropDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CropDetailsRepository extends JpaRepository<CropDetails, Long> {
    Optional<CropDetails> findByCropId(Long cropId);
}
