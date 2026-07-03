package com.example.AccountService.dto;

import java.math.BigDecimal;

public class AccountResponse {

    private Long customerId;

    private Long accountNumber;

    private String phone;

    private String bankName;

    private String branchName;

    private String accountType;

    private BigDecimal balance;

    public AccountResponse() {
    }

    public AccountResponse(Long customerId, Long accountNumber, String phone,
                           String bankName, String branchName,
                           String accountType, BigDecimal balance) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.phone = phone;
        this.bankName = bankName;
        this.branchName = branchName;
        this.accountType = accountType;
        this.balance = balance;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}