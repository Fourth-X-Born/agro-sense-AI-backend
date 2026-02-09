package com.agrosense.backend.service;

import com.agrosense.backend.dto.cropguide.CropGuideResponse;

public interface CropGuideService {
    CropGuideResponse getCropGuide(Long cropId);
}
