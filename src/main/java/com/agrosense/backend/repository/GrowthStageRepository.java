package com.agrosense.backend.repository;

import com.agrosense.backend.entity.GrowthStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrowthStageRepository extends JpaRepository<GrowthStage, Long> {
    List<GrowthStage> findByCropIdOrderByStageOrder(Long cropId);
}
