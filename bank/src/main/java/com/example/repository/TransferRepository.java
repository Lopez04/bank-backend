package com.example.repository;

import com.example.constan.TransferStatus;
import com.example.dto.transfer.TransferRequest;
import com.example.entity.AccountsEntity;
import com.example.entity.TransfersEntity;
import com.example.utils.NumberGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TransferRepository extends AbstractRepository<TransfersEntity>{

    @PersistenceContext
    EntityManager em;
    public TransferRepository(){
        super(TransfersEntity.class);
    }

    @Transactional
    public void createTransfer(TransferRequest tr, AccountsEntity OA, AccountsEntity DA){

        TransfersEntity te = new TransfersEntity();
        te.setType(tr.type);
        te.setAmount(tr.amount);
        te.setDescription(tr.description);
        te.setOriginAccount(OA);
        te.setDestinationAccount(DA);
        te.setReferenceCode(NumberGenerator.generateReference());
        te.setStatus(TransferStatus.PENDING);

        em.persist(te);
    }
}
