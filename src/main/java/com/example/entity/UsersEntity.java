package com.example.entity;

import com.example.constan.UserRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_tb")
public class UsersEntity {

    @Id
    @GeneratedValue
    private UUID idUser;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Boolean active;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist
    public void prePersist(){
        if(active == null){
            this.active = true;
        }
        if(role == null){
            this.role = UserRole.CLIENT;
        }

        this.createAt= LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updateAt= LocalDateTime.now();
    }

    public UsersEntity (){}
    public UsersEntity (UsersEntity ue){
        this.userName = ue.userName;
        this.firstName = ue.firstName;
        this.lastName = ue.lastName;
        this.email = ue.email;
        this.password = ue.password;
        this.role = ue.role;
        this.active = ue.active;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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
}
