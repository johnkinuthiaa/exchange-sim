package com.slippery.accountservice.controller;

import com.slippery.accountservice.dto.AccountCreationRequest;
import com.slippery.accountservice.dto.AccountResponse;
import com.slippery.accountservice.models.AccountType;
import com.slippery.accountservice.service.AccountService;
import jakarta.validation.constraints.NotBlank;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<AccountResponse> createNewAccount(@RequestBody AccountCreationRequest request) {
        var createdAccount =service.createNewAccount(request.getUserId(),request.getAccountType());
        return ResponseEntity.status(HttpStatusCode.valueOf(createdAccount.getStatusCode())).body(createdAccount);
    }
    @PatchMapping("/{userId}/reset/{accountId}")
    public ResponseEntity<AccountResponse> resetDemoAccount(
            @PathVariable String userId,
            @PathVariable String accountId) {
        AccountResponse resetAccount =service.resetDemoAccount(userId, accountId);
        return ResponseEntity.status(HttpStatusCode.valueOf(resetAccount.getStatusCode())).body(resetAccount);
    }

    public AccountResponse deleteUserAccount(String userId, String accountId) {
        return null;
    }

    public AccountResponse getAccountById(String userId, String accountId) {
        return null;
    }
    @GetMapping("/{userId}/get/{accountType}")
    public ResponseEntity<AccountResponse> getUserAccount(@PathVariable @NotBlank String userId,
                                                          @PathVariable AccountType accountType) {
        var foundAccount =service.getUserAccount(userId, accountType);
        return ResponseEntity.status(HttpStatusCode.valueOf(foundAccount.getStatusCode())).body(foundAccount);
    }

    public AccountResponse updateAccountBalance(String userId, String accountId, BigInteger profitOrLoss) {
        return null;
    }
}
