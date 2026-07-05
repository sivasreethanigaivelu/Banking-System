package com.example.AccountService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Column(nullable = false)
    private Long customerId;

    @Id
    @Column(nullable = false, unique = true)
    private Long accountNumber;

    @Column(nullable = false, unique = true, length = 10)
    private String phone;

    @Column(nullable = false, length = 4)
    private String pin;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private String branchName;

    @Column(nullable = false)
    private String accountType;

    @Column(nullable = false)
    private BigDecimal initialDeposit;

    public Account() {
    }

    public Account(Long customerId, Long accountNumber, String phone, String pin,
                   String bankName, String branchName, String accountType,
                   BigDecimal initialDeposit) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.phone = phone;
        this.pin = pin;
        this.bankName = bankName;
        this.branchName = branchName;
        this.accountType = accountType;
        this.initialDeposit = initialDeposit;
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