package com.agrosense.backend.repository;

import com.agrosense.backend.entity.FertilizerRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FertilizerRecommendationRepository extends JpaRepository<FertilizerRecommendation, Long> {

    List<FertilizerRecommendation> findByCropId(Long cropId);

    List<FertilizerRecommendation> findByFertilizerType(String fertilizerType);

    List<FertilizerRecommendation> findByCropIdAndFertilizerType(Long cropId, String fertilizerType);
}
