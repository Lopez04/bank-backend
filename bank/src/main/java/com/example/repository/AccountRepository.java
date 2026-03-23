package com.example.repository;

import com.example.dto.account.AccountRequest;
import com.example.entity.AccountsEntity;
import com.example.entity.TransfersEntity;
import com.example.entity.UsersEntity;
import com.example.utils.NumberGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountRepository extends AbstractRepository<AccountsEntity>{
    @PersistenceContext
    EntityManager em;

    public AccountRepository(){
        super(AccountsEntity.class);
    }


    @Transactional
    public String create(AccountRequest ar, UsersEntity us){
        AccountsEntity ae = new AccountsEntity();
        ae.setType(ar.type);
        ae.setCurrency(ar.currency);
        ae.setBalance(ar.balance);
        ae.setAccountNumber(NumberGenerator.generateAccountNumber(9));
        ae.setUser(us);

        em.persist(ae);
        return ae.getAccountNumber();
    }

    public boolean isViable(String NA, float transfer){

        AccountsEntity ae = em.createQuery("select e from AccountsEntity e where e.accountNumber = :NAccount", AccountsEntity.class)
                .setParameter("NAccount", NA)
                .getSingleResult();

        return ae.getBalance() >= transfer;
    }

    public AccountsEntity findByNA(String NA){

        return em.createQuery("select e from AccountsEntity e where e.accountNumber = :NAccount", AccountsEntity.class)
                .setParameter("NAccount", NA)
                .getSingleResult();

    }

    public List<String> AccountListUser(UUID idUser){

        List<String> Accounts = em.createQuery("select e.accountNumber from AccountsEntity e where e.user.idUser = :idUser", String.class)
                .setParameter("idUser", idUser)
                .getResultList();

        if(Accounts.isEmpty()){
            return null;
        }

        return Accounts;

    }

    public List<AccountsEntity> AccountsUser(UUID idUser){

        List<AccountsEntity> Accounts = em.createQuery("select e from AccountsEntity e where e.user.idUser = :idUser", AccountsEntity.class)
                .setParameter("idUser", idUser)
                .getResultList();

        if(Accounts.isEmpty()){
            return null;
        }

        return Accounts;

    }


}
