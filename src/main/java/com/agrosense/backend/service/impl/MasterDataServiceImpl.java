package com.agrosense.backend.service.impl;

import com.agrosense.backend.dto.CropDto;
import com.agrosense.backend.dto.DistrictDto;
import com.agrosense.backend.entity.Crop;
import com.agrosense.backend.entity.District;
import com.agrosense.backend.repository.CropRepository;
import com.agrosense.backend.repository.DistrictRepository;
import com.agrosense.backend.service.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterDataServiceImpl implements MasterDataService {

    private final CropRepository cropRepository;
    private final DistrictRepository districtRepository;

    @Override
    public List<CropDto> getCrops() {
        return cropRepository.findAll()
                .stream()
                .map(this::toCropDto)
                .toList();
    }

    @Override
    public List<DistrictDto> getDistricts() {
        return districtRepository.findAll()
                .stream()
                .map(this::toDistrictDto)
                .toList();
    }

    private CropDto toCropDto(Crop crop) {
        return new CropDto(crop.getId(), crop.getName());
    }

    private DistrictDto toDistrictDto(District district) {
        return new DistrictDto(district.getId(), district.getName());
    }
}
