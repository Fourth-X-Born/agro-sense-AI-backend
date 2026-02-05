package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.DistrictDto;
import com.agrosense.backend.service.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final MasterDataService masterDataService;

    @GetMapping
    public ApiResponse<List<DistrictDto>> getDistricts() {
        List<DistrictDto> districts = masterDataService.getDistricts();
        return new ApiResponse<>(true, "Districts fetched", districts);
    }
}
