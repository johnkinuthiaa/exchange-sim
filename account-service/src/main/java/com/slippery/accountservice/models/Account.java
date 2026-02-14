package com.slippery.accountservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLocking;

import java.math.BigInteger;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@OptimisticLocking
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private BigInteger balance;
    private Date lastUpdated =new Date();
    private BigInteger availableFunds;
    private AccountType accountType;
    private BigInteger realizedProfitsAndLosses;
    private BigInteger unrealizedProfitsAndLosses;
    private BigInteger equity;
    @Version
    private Integer version;


}
