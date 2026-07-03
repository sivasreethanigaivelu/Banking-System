package com.example.AccountService.controller;

import com.example.AccountService.dto.*;
import com.example.AccountService.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // CREATE (only ADMIN or logged user)
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    // GET ACCOUNT (ROLE CHECK)
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable Long accountNumber,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        Long customerId = (Long) request.getAttribute("customerId");

        AccountResponse response =
                accountService.getAccountByAccountNumber(accountNumber);

        // CUSTOMER can only see own account
        if ("CUSTOMER".equals(role)
                && !response.getCustomerId().equals(customerId)) {
            throw new RuntimeException("Access denied: Not your account");
        }

        return ResponseEntity.ok(response);
    }

    // GET ALL ACCOUNTS (ADMIN ONLY)
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts(
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Only ADMIN can view all accounts");
        }

        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // DELETE (ADMIN ONLY)
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> deleteAccount(
            @PathVariable Long accountNumber,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Only ADMIN can delete accounts");
        }

        accountService.deleteAccount(accountNumber);

        return ResponseEntity.ok("Deleted successfully");
    }

    // BALANCE (OWNER ONLY)
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<BalanceResponse> getBalance(
            @PathVariable Long accountNumber,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        Long customerId = (Long) request.getAttribute("customerId");

        AccountResponse account =
                accountService.getAccountByAccountNumber(accountNumber);

        if ("CUSTOMER".equals(role)
                && !account.getCustomerId().equals(customerId)) {
            throw new RuntimeException("Access denied");
        }

        return ResponseEntity.ok(
                accountService.getBalance(accountNumber)
        );
    }
    // UPDATE BALANCE (Called by Transaction Service)
    @PostMapping("/update-balance")
    public ResponseEntity<String> updateBalance(
            @RequestParam Long accountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String type) {

        accountService.updateBalance(accountNumber, amount, type);

        return ResponseEntity.ok("Balance updated successfully");
    }
}