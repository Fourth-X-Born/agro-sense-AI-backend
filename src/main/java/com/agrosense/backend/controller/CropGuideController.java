package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.cropguide.CropGuideResponse;
import com.agrosense.backend.service.CropGuideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crop-guide")
@RequiredArgsConstructor
public class CropGuideController {

    private final CropGuideService cropGuideService;

    @GetMapping("/{cropId}")
    public ResponseEntity<ApiResponse<CropGuideResponse>> getCropGuide(@PathVariable("cropId") Long cropId) {
        CropGuideResponse guide = cropGuideService.getCropGuide(cropId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Crop guide retrieved successfully", guide));
    }
}
