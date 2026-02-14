package com.slippery.accountservice.service.impl;

import com.slippery.accountservice.dto.AccountResponse;
import com.slippery.accountservice.repository.AccountRepository;
import com.slippery.accountservice.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountResponse createNewAccount(String userId) {
        return null;
    }

    @Override
    public AccountResponse resetDemoAccount(String userId, String accountId) {
        return null;
    }

    @Override
    public AccountResponse deleteUserAccount(String userId, String accountId) {
        return null;
    }

    @Override
    public AccountResponse getAccountById(String userId, String accountId) {
        return null;
    }

    @Override
    public AccountResponse updateAccountBalance(String userId, String accountId, BigInteger profitOrLoss) {
        return null;
    }
    private boolean UserExists(String userId){

        return false;
    }
}
