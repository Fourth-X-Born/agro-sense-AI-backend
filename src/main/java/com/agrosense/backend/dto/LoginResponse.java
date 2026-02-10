package com.agrosense.backend.dto;

public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Long districtId;
    private String district;
    private Long cropId;
    private String crop;
    private String profilePhoto;
    private String token; // For future JWT use, currently can be null or a dummy token

    public LoginResponse(Long id, String name, String email, String phone, Long districtId, String district,
            Long cropId, String crop, String profilePhoto, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.districtId = districtId;
        this.district = district;
        this.cropId = cropId;
        this.crop = crop;
        this.profilePhoto = profilePhoto;
        this.token = token;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getCropId() {
        return cropId;
    }

    public void setCropId(Long cropId) {
        this.cropId = cropId;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
