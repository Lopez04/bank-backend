package com.example.dto.account;


import com.example.constan.AccountType;
import com.example.constan.UserRole;
import com.example.entity.UsersEntity;

import java.util.UUID;

public class AccountRequest {
    public float balance;
    public String currency;
    public AccountType type;
    public Boolean active;
    public UUID idUser;
    public UserRole userRole;
}
