package com.example.auth_service.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegisterRequest {


        @NotBlank(message = "Full Name is required")
        private String fullName;

        @NotBlank(message = "Phone Number is required")
        @Pattern(regexp = "^[6-9]\\d{9}$",
                message = "Phone Number must contain 10 digits")
        private String phone;

        @NotBlank(message = "Password is required")
        private String password;

        public RegisterRequest() {
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
