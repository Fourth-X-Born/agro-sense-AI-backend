package com.agrosense.backend.repository;

import com.agrosense.backend.entity.CropGuideline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropGuidelineRepository extends JpaRepository<CropGuideline, Long> {
    List<CropGuideline> findByCropIdOrderByPriority(Long cropId);

    List<CropGuideline> findByCropIdAndGuidelineTypeOrderByPriority(Long cropId, String guidelineType);
}
