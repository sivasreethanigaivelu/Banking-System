package com.example.AccountService.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class UpdateAccountRequest {

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$",
            message = "Phone Number must be a valid 10-digit Indian mobile number")
    private String phone;

    @NotBlank(message = "PIN is required")
    @Pattern(regexp = "^\\d{4}$",
            message = "PIN must contain exactly 4 digits")
    private String pin;

    @NotBlank(message = "Bank Name is required")
    @Pattern(regexp = "^[A-Za-z ]+$",
            message = "Bank Name should contain only alphabets")
    private String bankName;

    @NotBlank(message = "Branch Name is required")
    @Pattern(regexp = "^[A-Za-z ]+$",
            message = "Branch Name should contain only alphabets")
    private String branchName;

    @NotBlank(message = "Account Type is required")
    @Pattern(regexp = "^(SAVINGS|CURRENT)$",
            message = "Account Type must be SAVINGS or CURRENT")
    private String accountType;

    @NotNull(message = "Initial Deposit is required")
    @DecimalMin(value = "1000.00",
            message = "Minimum Initial Deposit is ₹1000")
    private BigDecimal initialDeposit;

    public UpdateAccountRequest() {
    }

    public UpdateAccountRequest(String phone, String pin, String bankName,
                                String branchName, String accountType,
                                BigDecimal initialDeposit) {
        this.phone = phone;
        this.pin = pin;
        this.bankName = bankName;
        this.branchName = branchName;
        this.accountType = accountType;
        this.initialDeposit = initialDeposit;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(BigDecimal initialDeposit) {
        this.initialDeposit = initialDeposit;
    }
}