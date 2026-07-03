package com.example.AccountService.service.impl;

import java.math.BigDecimal;
import com.example.AccountService.dto.*;
import com.example.AccountService.entity.Account;
import com.example.AccountService.exception.AccountNotFoundException;
import com.example.AccountService.exception.DuplicateAccountException;
import com.example.AccountService.repository.AccountRepository;
import com.example.AccountService.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // ---------------- CREATE ACCOUNT ----------------
    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {

        // 1. Duplicate check
        if (accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new DuplicateAccountException("Account already exists");
        }

        if (accountRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateAccountException("Phone already linked to another account");
        }

        // 2. Validate 16-digit account number
        if (String.valueOf(request.getAccountNumber()).length() != 16) {
            throw new IllegalArgumentException("Account number must be 16 digits");
        }

        // 3. Create entity
        Account account = new Account();
        account.setCustomerId(request.getCustomerId());
        account.setAccountNumber(request.getAccountNumber());
        account.setPhone(request.getPhone());
        account.setPin(request.getPin());
        account.setBankName(request.getBankName());
        account.setBranchName(request.getBranchName());
        account.setAccountType(request.getAccountType());

        // initial deposit becomes balance logic
        account.setInitialDeposit(request.getInitialDeposit());

        accountRepository.save(account);

        // 4. Response
        return mapToResponse(account);
    }

    // ---------------- GET ACCOUNT ----------------
    @Override
    public AccountResponse getAccountByAccountNumber(Long accountNumber) {

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found: " + accountNumber));

        return mapToResponse(account);
    }

    // ---------------- GET ALL ACCOUNTS ----------------
    @Override
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ---------------- UPDATE ACCOUNT ----------------
    @Override
    public AccountResponse updateAccount(Long accountNumber, UpdateAccountRequest request) {

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found: " + accountNumber));


        account.setPhone(request.getPhone());
        account.setPin(request.getPin());
        account.setBankName(request.getBankName());
        account.setBranchName(request.getBranchName());
        account.setAccountType(request.getAccountType());
        account.setInitialDeposit(request.getInitialDeposit());

        accountRepository.save(account);

        return mapToResponse(account);
    }

    // ---------------- PATCH ACCOUNT ----------------
    @Override
    public AccountResponse patchAccount(Long accountNumber, PatchAccountRequest request) {

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found: " + accountNumber));

        if (request.getPhone() != null) {
            account.setPhone(request.getPhone());
        }

        if (request.getPin() != null) {
            account.setPin(request.getPin());
        }

        if (request.getBankName() != null) {
            account.setBankName(request.getBankName());
        }

        if (request.getBranchName() != null) {
            account.setBranchName(request.getBranchName());
        }

        if (request.getAccountType() != null) {
            account.setAccountType(request.getAccountType());
        }

        if (request.getInitialDeposit() != null) {
            account.setInitialDeposit(request.getInitialDeposit());
        }

        accountRepository.save(account);

        return mapToResponse(account);
    }

    // ---------------- DELETE ACCOUNT ----------------
    @Override
    public void deleteAccount(Long accountNumber) {

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found: " + accountNumber));

        accountRepository.delete(account);
    }

    // ---------------- BALANCE ----------------
    @Override
    public BalanceResponse getBalance(Long accountNumber) {

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found: " + accountNumber));

        return new BalanceResponse(account.getAccountNumber(),
                account.getInitialDeposit());
    }
    // ---------------- UPDATE BALANCE ----------------
    @Override
    public void updateBalance(Long accountNumber, BigDecimal amount, String type) {

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found: " + accountNumber));

        if (type.equalsIgnoreCase("DEPOSIT")) {

            account.setInitialDeposit(
                    account.getInitialDeposit().add(amount)
            );

        } else if (type.equalsIgnoreCase("WITHDRAW")) {

            if (account.getInitialDeposit().compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient Balance");
            }

            account.setInitialDeposit(
                    account.getInitialDeposit().subtract(amount)
            );

        } else {

            throw new IllegalArgumentException("Invalid transaction type");

        }

        accountRepository.save(account);
    }

    // ---------------- MAPPER ----------------
    private AccountResponse mapToResponse(Account account) {

        AccountResponse response = new AccountResponse();
        response.setCustomerId(account.getCustomerId());
        response.setAccountNumber(account.getAccountNumber());
        response.setPhone(account.getPhone());
        response.setBankName(account.getBankName());
        response.setBranchName(account.getBranchName());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getInitialDeposit());

        return response;
    }
}