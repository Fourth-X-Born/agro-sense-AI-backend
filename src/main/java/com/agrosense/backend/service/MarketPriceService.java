package com.agrosense.backend.service;

import com.agrosense.backend.dto.MarketPriceDto;
import java.util.List;

public interface MarketPriceService {

    List<MarketPriceDto> getAllPrices();

    List<MarketPriceDto> getPricesByCrop(Long cropId);

    List<MarketPriceDto> getPricesByCropAndDistrict(Long cropId, Long districtId);

    List<MarketPriceDto> getPricesByDistrict(Long districtId);
}
