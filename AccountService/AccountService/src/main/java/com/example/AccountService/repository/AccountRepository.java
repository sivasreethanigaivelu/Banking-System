package com.example.AccountService.repository;

import com.example.AccountService.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(Long accountNumber);

    boolean existsByPhone(String phone);

    Account findByAccountNumber(Long accountNumber);

    Account findByPhone(String phone);

}
