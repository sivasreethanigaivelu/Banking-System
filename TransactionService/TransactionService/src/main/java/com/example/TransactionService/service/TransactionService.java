package com.example.TransactionService.service;

import com.example.TransactionService.dto.*;
import com.example.TransactionService.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction deposit(DepositRequest request);

    Transaction withdraw(WithdrawRequest request);

    String transfer(TransferRequest request);

    List<Transaction> history(Long accountNumber);
}