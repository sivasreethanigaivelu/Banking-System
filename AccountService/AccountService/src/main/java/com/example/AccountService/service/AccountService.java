package com.example.AccountService.service;

import com.example.AccountService.dto.*;
import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest request);

    AccountResponse getAccountByAccountNumber(Long accountNumber);

    List<AccountResponse> getAllAccounts();

    AccountResponse updateAccount(Long accountNumber, UpdateAccountRequest request);

    AccountResponse patchAccount(Long accountNumber, PatchAccountRequest request);

    void deleteAccount(Long accountNumber);

    BalanceResponse getBalance(Long accountNumber);

    // New method
    void updateBalance(Long accountNumber, BigDecimal amount, String type);
}