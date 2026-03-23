package com.example.service;

import com.example.dto.auth.LoginRequest;
import com.example.dto.user.UserResponse;
import com.example.entity.UsersEntity;
import com.example.repository.AccountRepository;
import com.example.repository.UserRepository;
import com.example.utils.GenerateToken;
import com.example.utils.PasswordUtils;
import com.example.utils.ResponseUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;


@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    AccountRepository accountRepository;

    public Response login(LoginRequest lr){

        List<UsersEntity> existAccount = userRepository.existLoginAccount(lr);

        if(existAccount.isEmpty()){
            return ResponseUtil.error("Credenciales inválidas", Response.Status.BAD_REQUEST);
        }

        UsersEntity ue = existAccount.get(0);

        boolean verifyPass = PasswordUtils.verify(lr.password, ue.getPassword());

        if (!verifyPass) {
            return ResponseUtil.error("Credenciales inválidas", Response.Status.BAD_REQUEST);
        }
        GenerateToken tk = new GenerateToken();
        UserResponse ur = new UserResponse();

        List<String> accounts = accountRepository.AccountListUser(ue.getIdUser());

        ur.userName = ue.getUserName();
        ur.lastName = ue.getLastName();
        ur.firstName = ue.getFirstName();
        ur.role = ue.getRole();
        ur.email = ue.getEmail();
        ur.userAccounts = accounts;
        ur.token = tk.generateToken(ue);

        return ResponseUtil.ok("Inicio Exitoso", ur);
    }
}
