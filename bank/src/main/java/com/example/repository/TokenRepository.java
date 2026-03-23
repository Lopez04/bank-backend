package com.example.repository;

import com.example.entity.TokensEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenRepository extends AbstractRepository<TokensEntity>{
    public TokenRepository(){
        super(TokensEntity.class);
    }

}
