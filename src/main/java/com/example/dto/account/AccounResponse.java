package com.example.dto.account;

import com.example.constan.AccountType;

import java.time.LocalDateTime;

public class AccounResponse {
    public String accountNumber;
    public float balance;
    public String currency;
    public AccountType type;
    public Boolean active;
    public String owner;
    public LocalDateTime createAt;
    public LocalDateTime updateAt;
}
