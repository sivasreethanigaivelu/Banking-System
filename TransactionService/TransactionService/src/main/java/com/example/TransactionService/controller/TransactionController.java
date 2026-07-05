package com.example.TransactionService.controller;

import com.example.TransactionService.dto.*;
import com.example.TransactionService.entity.Transaction;
import com.example.TransactionService.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/deposit")
    public ApiResponse deposit(@RequestBody DepositRequest request) {
        return new ApiResponse("Deposit Successful", service.deposit(request)
        );
    }

    @PostMapping("/withdraw")
    public ApiResponse withdraw(@RequestBody WithdrawRequest request) {
        return new ApiResponse("Withdraw Successful", service.withdraw(request)
        );
    }

    @PostMapping("/transfer")
    public ApiResponse transfer(@RequestBody TransferRequest request) {
        return new ApiResponse("Transfer Completed", service.transfer(request)
        );
    }

    @GetMapping("/{accountNumber}")
    public List<Transaction> history(@PathVariable Long accountNumber) {
        return service.history(accountNumber);
    }
}