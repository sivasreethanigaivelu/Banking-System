package com.example.AccountService.controller;

import com.example.AccountService.dto.*;
import com.example.AccountService.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable Long accountNumber,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        Long customerId = (Long) request.getAttribute("customerId");

        System.out.println("\n========== GET ACCOUNT ==========");
        System.out.println("Role       : " + role);
        System.out.println("CustomerId : " + customerId);
        System.out.println("=================================\n");

        AccountResponse response = accountService.getAccountByAccountNumber(accountNumber);

        if ("CUSTOMER".equals(role) && !response.getCustomerId().equals(customerId)) {
            throw new RuntimeException("Access denied: Not your account");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts(HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        Long customerId = (Long) request.getAttribute("customerId");

        System.out.println("\n========== ACCOUNT CONTROLLER ==========");
        System.out.println("Role from Request      : " + role);
        System.out.println("CustomerId from Request: " + customerId);
        System.out.println("Authentication         : "
                + SecurityContextHolder.getContext().getAuthentication());
        System.out.println("Authorities            : "
                + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        System.out.println("========================================\n");

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Only ADMIN can view all accounts");
        }

        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable Long accountNumber,
            @RequestBody UpdateAccountRequest updateRequest,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        Long customerId = (Long) request.getAttribute("customerId");

        AccountResponse account = accountService.getAccountByAccountNumber(accountNumber);

        if ("CUSTOMER".equals(role)
                && !account.getCustomerId().equals(customerId)) {
            throw new RuntimeException("Access denied: You can update only your own account");
        }

        return ResponseEntity.ok(accountService.updateAccount(accountNumber, updateRequest)
        );
    }

    @PatchMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> patchAccount(
            @PathVariable Long accountNumber,
            @RequestBody PatchAccountRequest patchRequest,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        Long customerId = (Long) request.getAttribute("customerId");

        AccountResponse account = accountService.getAccountByAccountNumber(accountNumber);

        if ("CUSTOMER".equals(role) && !account.getCustomerId().equals(customerId)) {
            throw new RuntimeException("Access denied: You can update only your own account");
        }

        return ResponseEntity.ok(accountService.patchAccount(accountNumber, patchRequest)
        );
    }

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


    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<BalanceResponse> getBalance(
            @PathVariable Long accountNumber,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        Long customerId = (Long) request.getAttribute("customerId");

        AccountResponse account = accountService.getAccountByAccountNumber(accountNumber);

        if ("CUSTOMER".equals(role) && !account.getCustomerId().equals(customerId)) {
            throw new RuntimeException("Access denied");
        }

        return ResponseEntity.ok(
                accountService.getBalance(accountNumber)
        );
    }

    @PostMapping("/update-balance")
    public ResponseEntity<String> updateBalance(
            @RequestParam Long accountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String type) {

        accountService.updateBalance(accountNumber, amount, type);

        return ResponseEntity.ok("Balance updated successfully");
    }
}