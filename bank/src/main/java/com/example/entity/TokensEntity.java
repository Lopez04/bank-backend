package com.example.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "token")
public class TokensEntity {


    @Id
    @GeneratedValue
    private UUID idToken;

    private String token;
    private LocalDateTime expirationDate;
    private Boolean revoked;
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "idUser_fk")
    private UsersEntity user;

    @PrePersist
    public void prePersist(){
        if(revoked == null){
            this.revoked = false;
        }

        createAt = LocalDateTime.now();
    }

    public TokensEntity(){}
    public TokensEntity(TokensEntity te){
        this.token = te.token;
        this.expirationDate = te.expirationDate;
        this.revoked = te.revoked;
        this.user = te.user;
    }

    public UUID getIdToken() {
        return idToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getRevoke() {
        return revoked;
    }

    public void setRevoke(Boolean revoked) {
        this.revoked = revoked;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }
}
