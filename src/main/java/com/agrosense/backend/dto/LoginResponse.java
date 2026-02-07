package com.agrosense.backend.dto;

public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String district;
    private String crop;
    private String token; // For future JWT use, currently can be null or a dummy token

    public LoginResponse(Long id, String name, String email, String phone, String district, String crop, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.district = district;
        this.crop = crop;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
