package com.slippery.accountservice.repository;

import com.slippery.accountservice.models.Account;
import com.slippery.accountservice.models.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,String> {
    Optional<List<Account>> getAccountByUserId(String userId);
    Optional<Account> getAccountByAccountTypeAndId(AccountType accountType,String id);
    Optional<Account> getAccountByUserIdAndAccountType(String userId,AccountType accountType);
}
