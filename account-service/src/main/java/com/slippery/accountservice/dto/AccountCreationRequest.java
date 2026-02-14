package com.slippery.accountservice.dto;

import com.slippery.accountservice.models.AccountType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountCreationRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private AccountType accountType;
}
