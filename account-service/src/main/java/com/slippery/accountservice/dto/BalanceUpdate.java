package com.slippery.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigInteger;

@Data
public class BalanceUpdate {
    private Long amount;
    @NotBlank
    private BalanceUpdateType balanceUpdateType;
//    incase of p2p and selling....implement this later after tumeadd izo service zingine


}
