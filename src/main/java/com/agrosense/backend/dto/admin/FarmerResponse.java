package com.agrosense.backend.dto.admin;

import com.agrosense.backend.entity.Farmer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmerResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private DistrictInfo district;
    private CropInfo crop;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DistrictInfo {
        private Long id;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CropInfo {
        private Long id;
        private String name;
    }

    public static FarmerResponse fromEntity(Farmer farmer) {
        FarmerResponseBuilder builder = FarmerResponse.builder()
                .id(farmer.getId())
                .name(farmer.getName())
                .email(farmer.getEmail())
                .phone(farmer.getPhone());

        // Safely handle district reference
        try {
            if (farmer.getDistrict() != null) {
                builder.district(DistrictInfo.builder()
                        .id(farmer.getDistrict().getId())
                        .name(farmer.getDistrict().getName())
                        .build());
            }
        } catch (Exception e) {
            // District reference invalid, set to null
            builder.district(null);
        }

        // Safely handle crop reference
        try {
            if (farmer.getCrop() != null) {
                builder.crop(CropInfo.builder()
                        .id(farmer.getCrop().getId())
                        .name(farmer.getCrop().getName())
                        .build());
            }
        } catch (Exception e) {
            // Crop reference invalid, set to null
            builder.crop(null);
        }

        return builder.build();
    }
}
