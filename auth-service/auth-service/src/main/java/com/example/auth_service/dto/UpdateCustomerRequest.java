package com.example.auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateCustomerRequest {

    @NotBlank(message = "Full Name is required")
    private String fullName;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String phone;

    public UpdateCustomerRequest() {
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
}
