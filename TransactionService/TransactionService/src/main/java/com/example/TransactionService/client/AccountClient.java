package com.example.TransactionService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "AccountService")
public interface AccountClient {

    @PostMapping("/accounts/update-balance")
    String updateBalance(
            @RequestParam("accountNumber") Long accountNumber,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("type") String type
    );
}