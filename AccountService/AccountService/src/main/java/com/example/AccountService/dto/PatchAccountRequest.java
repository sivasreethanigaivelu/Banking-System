package com.example.AccountService.dto;

import java.math.BigDecimal;

public class PatchAccountRequest {

    private String phone;

    private String pin;

    private String bankName;

    private String branchName;

    private String accountType;

    private BigDecimal initialDeposit;

    public PatchAccountRequest() {
    }

    public PatchAccountRequest(String phone, String pin, String bankName,
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