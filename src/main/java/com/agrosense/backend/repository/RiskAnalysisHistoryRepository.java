package com.agrosense.backend.repository;

import com.agrosense.backend.entity.RiskAnalysisHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiskAnalysisHistoryRepository extends JpaRepository<RiskAnalysisHistory, Long> {
    List<RiskAnalysisHistory> findByFarmerIdOrderByCreatedAtDesc(Long farmerId);
}
