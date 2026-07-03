package com.example.TransactionService.dto;

import java.math.BigDecimal;

public class WithdrawRequest {

    private Long accountNumber;
    private BigDecimal amount;

    public WithdrawRequest() {
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}