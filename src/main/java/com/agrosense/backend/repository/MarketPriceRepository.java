package com.agrosense.backend.repository;

import com.agrosense.backend.entity.MarketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {

    List<MarketPrice> findByCropId(Long cropId);

    List<MarketPrice> findByCropIdAndDistrictId(Long cropId, Long districtId);

    List<MarketPrice> findByDistrictId(Long districtId);

    Optional<MarketPrice> findByCropIdAndDistrictIdAndPriceDate(Long cropId, Long districtId, LocalDate priceDate);
}
