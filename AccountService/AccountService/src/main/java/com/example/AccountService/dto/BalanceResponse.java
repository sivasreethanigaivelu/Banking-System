package com.example.AccountService.dto;

import java.math.BigDecimal;

public class BalanceResponse {

    private Long accountNumber;

    private BigDecimal balance;

    public BalanceResponse() {
    }

    public BalanceResponse(Long accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}