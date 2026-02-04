package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.CropDto;
import com.agrosense.backend.service.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {

    private final MasterDataService masterDataService;

    @GetMapping
    public ApiResponse<List<CropDto>> getCrops() {
        List<CropDto> crops = masterDataService.getCrops();
        return new ApiResponse<>(true, "Crops fetched", crops);
    }
}
