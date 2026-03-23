package com.example.entity;

import com.example.constan.AccountType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account")
public class AccountsEntity {

    @Id
    @GeneratedValue
    private UUID idAccount;
    private String accountNumber;
    private float balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    private Boolean active;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    @JoinColumn(name = "idUser_fk")
    @ManyToOne
    private UsersEntity user;

    @PrePersist
    public void prePersist(){
        if(active == null){
            this.active = true;
        }
        this.createAt= LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updateAt= LocalDateTime.now();
    }

    public AccountsEntity(){}
    public AccountsEntity(AccountsEntity ae){
        this.accountNumber = ae.accountNumber;
        this.balance = ae.balance;
        this.currency = ae.currency;
        this.type = ae.type;
        this.active = ae.active;
        this.user=ae.user;
    }

    public UUID getIdAccount() {
        return idAccount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }
}
