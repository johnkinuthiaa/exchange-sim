package com.slippery.accountservice.service.impl;

import com.slippery.accountservice.client.UserResponse;
import com.slippery.accountservice.dto.AccountResponse;
import com.slippery.accountservice.models.Account;
import com.slippery.accountservice.models.AccountType;
import com.slippery.accountservice.repository.AccountRepository;
import com.slippery.accountservice.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    public AccountServiceImpl(AccountRepository repository,
                              RestClient.Builder restClientBuilder,
                              DiscoveryClient discoveryClient) {
        this.repository = repository;
        this.restClient = restClientBuilder.build();
        this.discoveryClient = discoveryClient;
    }

    @Override
    public AccountResponse createNewAccount(String userId, AccountType accountType) {
        AccountResponse response =new AccountResponse();
        if(userId.isEmpty()){
            response.setMessage("User id is mandatory to perform this action");
            response.setStatusCode(404);
            return response;
        }
        boolean existingUser =UserExists(userId);
        if(!existingUser){
            response.setMessage("User not found");
            response.setStatusCode(404);
            return response;
        }
        Optional<List<Account>> existingAccount =repository.getAccountByUserId(userId);
        if(existingAccount.isPresent()){
            var userAccounts =existingAccount
                    .get()
                    .stream()
                    .filter(accounts -> accounts.getAccountType().equals(accountType))
                    .toList();
            if(!userAccounts.isEmpty()){
                response.setMessage("A "+accountType+" already exists for the user");
                response.setStatusCode(409);
                return response;
            }
        }

        Account account =new Account();
        account.setAccountType(accountType);
        account.setLastUpdated(new Date());
        account.setUserId(userId);
        account.setRealizedProfitsAndLosses(BigInteger.ZERO);
        account.setUnrealizedProfitsAndLosses(BigInteger.ZERO);
        switch (accountType){
            case DEMO,FUNDED -> {
                account.setBalance(BigInteger.valueOf(100_000));
                account.setAvailableFunds(BigInteger.valueOf(100_000));
                account.setEquity(BigInteger.valueOf(100_000));
            }
            case REAL -> {
                account.setBalance(BigInteger.ZERO);
                account.setAvailableFunds(BigInteger.ZERO);
                account.setEquity(BigInteger.ZERO);
            }
        }
        repository.save(account);
        response.setMessage("New account created successfully");
        response.setStatusCode(201);
        response.setAccount(account);
        return response;
    }

    @Override
    public AccountResponse resetDemoAccount(String userId, String accountId) {
        AccountResponse accountResponse =new AccountResponse();
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
    public AccountResponse getUserAccount(String userId,AccountType accountType) {
        AccountResponse response =new AccountResponse();
        var existingUser =UserExists(userId);
        if(!existingUser){
            response.setMessage("User not found");
            response.setStatusCode(404);
            return response;
        }
        Optional<List<Account>> userAccounts =repository.getAccountByUserId(userId);
        if(userAccounts.isEmpty()){
            response.setMessage("User does not have any accounts");
            response.setStatusCode(404);
            return response;
        }
        List<Account> allAccounts =userAccounts.get();
        var existingAccount =allAccounts.stream()
                .filter(account -> account.getAccountType().equals(accountType))
                .findFirst();
        if(existingAccount.isEmpty()){
            response.setMessage("User does not have a"+ accountType+" account");
            response.setStatusCode(404);
            return response;
        }
        response.setAccount(existingAccount.get());
        response.setStatusCode(200);
        response.setMessage("User's "+accountType+" account found");
        return response;
    }

    @Override
    public AccountResponse updateAccountBalance(String userId, String accountId, BigInteger profitOrLoss) {
        return null;
    }
    private boolean UserExists(String userId){
//        TODO: Replace all rest calls with rpc
        ServiceInstance serviceInstance =discoveryClient.getInstances("user-service").get(0);
        log.info("uri: {}",serviceInstance.getUri());
        var existingUser =restClient.get()
                .uri(serviceInstance.getUri()+"/api/v1/users/"+userId)
                .retrieve()
                .body(UserResponse.class);
        assert existingUser != null;
        return existingUser.getStatusCode() ==200;

    }
}
