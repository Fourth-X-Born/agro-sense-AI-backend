package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.MarketPriceDto;
import com.agrosense.backend.service.MarketPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market-prices")
@RequiredArgsConstructor
public class MarketPriceController {

    private final MarketPriceService marketPriceService;

    @GetMapping
    public ApiResponse<List<MarketPriceDto>> getMarketPrices(
            @RequestParam(required = false) Long cropId,
            @RequestParam(required = false) Long districtId) {

        List<MarketPriceDto> prices;

        if (cropId != null && districtId != null) {
            prices = marketPriceService.getPricesByCropAndDistrict(cropId, districtId);
        } else if (cropId != null) {
            prices = marketPriceService.getPricesByCrop(cropId);
        } else if (districtId != null) {
            prices = marketPriceService.getPricesByDistrict(districtId);
        } else {
            prices = marketPriceService.getAllPrices();
        }

        return new ApiResponse<>(true, "Market prices fetched", prices);
    }
}
