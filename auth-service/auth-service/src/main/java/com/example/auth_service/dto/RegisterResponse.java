package com.example.auth_service.dto;

public class RegisterResponse {

    private String message;
    private String role;

    public RegisterResponse() {
    }

    public RegisterResponse(String message, String role) {
        this.message = message;
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
