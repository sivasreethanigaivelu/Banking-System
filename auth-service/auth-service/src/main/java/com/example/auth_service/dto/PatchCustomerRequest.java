package com.example.auth_service.dto;

public class PatchCustomerRequest {

    private String fullName;
    private String phone;

    public PatchCustomerRequest() {
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
