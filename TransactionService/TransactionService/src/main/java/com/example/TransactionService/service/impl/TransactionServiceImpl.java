package com.example.TransactionService.service.impl;

import com.example.TransactionService.client.AccountClient;
import com.example.TransactionService.dto.DepositRequest;
import com.example.TransactionService.dto.TransferRequest;
import com.example.TransactionService.dto.WithdrawRequest;
import com.example.TransactionService.entity.Transaction;
import com.example.TransactionService.repository.TransactionRepository;
import com.example.TransactionService.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository repo;

    @Autowired
    private AccountClient accountClient;

    @Override
    public Transaction deposit(DepositRequest request) {

        accountClient.updateBalance(
                request.getAccountNumber(),
                request.getAmount(),
                "DEPOSIT"
        );

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(request.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setType("DEPOSIT");
        transaction.setStatus("SUCCESS");

        return repo.save(transaction);
    }

    @Override
    public Transaction withdraw(WithdrawRequest request) {

        accountClient.updateBalance(
                request.getAccountNumber(),
                request.getAmount(),
                "WITHDRAW"
        );

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(request.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setType("WITHDRAW");
        transaction.setStatus("SUCCESS");

        return repo.save(transaction);
    }

    @Override
    public String transfer(TransferRequest request) {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            Callable<Boolean> debitTask = () -> {accountClient.updateBalance(
                        request.getFromAccount(),
                        request.getAmount(),
                        "WITHDRAW"
                );
                return true;
            };

            Callable<Boolean> creditTask = () -> {accountClient.updateBalance(
                        request.getToAccount(),
                        request.getAmount(),
                        "DEPOSIT"
                );
                return true;
            };

            Future<Boolean> debitFuture = executor.submit(debitTask);
            Future<Boolean> creditFuture = executor.submit(creditTask);

            Boolean debitSuccess = debitFuture.get();
            Boolean creditSuccess = creditFuture.get();

            if (Boolean.TRUE.equals(debitSuccess) && Boolean.TRUE.equals(creditSuccess)) {

                Transaction transaction = new Transaction();
                transaction.setAccountNumber(request.getFromAccount());
                transaction.setAmount(request.getAmount());
                transaction.setType("TRANSFER");
                transaction.setStatus("SUCCESS");

                repo.save(transaction);

                return "Transfer Successful";
            }
        }
        catch (Exception e) {
            try {
                accountClient.updateBalance(
                        request.getFromAccount(),
                        request.getAmount(),
                        "DEPOSIT"
                );

                return "Transfer Failed. Rollback Successful.";
            }
            catch (Exception rollbackException) {

                return "Transfer Failed. Rollback Failed.";

            }

        } finally {

            executor.shutdown();

        }

        return "Transfer Failed.";
    }

    @Override
    public List<Transaction> history(Long accountNumber) {

        return repo.findByAccountNumber(accountNumber);

    }
}