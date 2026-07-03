package com.example.auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AdminRegisterRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String phone;

    @NotBlank
    private String password;

    public AdminRegisterRequest() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}