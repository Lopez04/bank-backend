package com.example.utils;

import com.example.dto.user.UserResponse;
import com.example.entity.UsersEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.smallrye.jwt.build.Jwt;

import java.util.Map;
import java.util.Set;

public class GenerateToken {

    public String generateToken(UsersEntity ue) {

            String TK = Jwt.issuer("my-bank")
                    .upn(ue.getIdUser().toString())
                    .groups(Set.of(ue.getRole().name()))
                    .expiresIn(30)
                    .sign();

            System.out.println(TK);
            return TK;
    }

}
