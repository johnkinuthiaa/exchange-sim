package com.slippery.accountservice.repository;

import com.slippery.accountservice.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
