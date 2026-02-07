package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.MarketPriceDto;
import com.agrosense.backend.entity.MarketPrice;
import com.agrosense.backend.repository.MarketPriceRepository;
import com.agrosense.backend.service.MarketPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketPriceServiceImpl implements MarketPriceService {

    private final MarketPriceRepository marketPriceRepository;

    @Override
    public List<MarketPriceDto> getAllPrices() {
        return marketPriceRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<MarketPriceDto> getPricesByCrop(Long cropId) {
        return marketPriceRepository.findByCropId(cropId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<MarketPriceDto> getPricesByCropAndDistrict(Long cropId, Long districtId) {
        return marketPriceRepository.findByCropIdAndDistrictId(cropId, districtId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<MarketPriceDto> getPricesByDistrict(Long districtId) {
        return marketPriceRepository.findByDistrictId(districtId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private MarketPriceDto toDto(MarketPrice marketPrice) {
        return new MarketPriceDto(
                marketPrice.getId(),
                marketPrice.getCrop().getId(),
                marketPrice.getCrop().getName(),
                marketPrice.getDistrict().getId(),
                marketPrice.getDistrict().getName(),
                marketPrice.getPricePerKg(),
                marketPrice.getPriceDate());
    }
}
