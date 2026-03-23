package com.example.repository;

import com.example.constan.UserRole;
import com.example.dto.auth.LoginRequest;
import com.example.dto.user.UserRequest;
import com.example.dto.user.UserResponse;
import com.example.entity.UsersEntity;
import com.example.utils.GenerateToken;
import com.example.utils.PasswordUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class UserRepository extends AbstractRepository<UsersEntity>{

    @PersistenceContext
    EntityManager em;

    public UserRepository(){
        super(UsersEntity.class);
    }

    public Boolean existEmail(String email){
        Long count = em.createQuery("select count(e) from UsersEntity e where email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }

    @Transactional
    public String createData(UserRequest ur){
        UsersEntity user = new UsersEntity();
        user.setEmail(ur.email);
        user.setUserName(ur.userName.isEmpty()?ur.firstName:ur.userName);
        user.setLastName(ur.lastName);
        user.setPassword(PasswordUtils.hash(ur.password));
        user.setFirstName(ur.firstName);
        user.setRole(ur.role);
        em.persist(user);
        return "Usuario creado con exito.";
    }

    public List<UsersEntity> existLoginAccount(LoginRequest lr){
        return em.createQuery("select e from UsersEntity e where e.userName = :identifier or e.email = :identifier",UsersEntity.class)
                .setParameter("identifier", lr.identifier)
                .getResultList();

    }



}
