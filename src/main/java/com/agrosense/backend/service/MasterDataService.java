package com.agrosense.backend.service;

import com.agrosense.backend.dto.CropDto;
import com.agrosense.backend.dto.DistrictDto;

import java.util.List;

public interface MasterDataService {
    List<CropDto> getCrops();
    List<DistrictDto> getDistricts();
}
