package com.slippery.accountservice.service;

import com.slippery.accountservice.dto.AccountResponse;

import java.math.BigInteger;

public interface AccountService {
    AccountResponse createNewAccount(String userId);
    AccountResponse resetDemoAccount(String userId,String accountId);
    AccountResponse deleteUserAccount(String userId,String accountId);
    AccountResponse getAccountById(String userId,String accountId);
    AccountResponse updateAccountBalance(String userId, String accountId, BigInteger profitOrLoss);
}
