package com.slippery.accountservice.dto;

import com.slippery.accountservice.models.AccountType;
import lombok.Data;

@Data
public class AccountCreationRequest {
    private String userId;
    private AccountType accountType;
}
