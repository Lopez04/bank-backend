package com.example.dto.user;

import com.example.constan.UserRole;

import java.util.List;

public class UserResponse {
    public String firstName;
    public String lastName;
    public String userName;
    public UserRole role;
    public List<String> userAccounts;
    public String email;
    public String token;
}
