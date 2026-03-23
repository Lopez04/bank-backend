package com.example.service;

import com.example.constan.TransferStatus;
import com.example.constan.TransferType;
import com.example.dto.transfer.TransferRequest;
import com.example.dto.transfer.TransferResponse;
import com.example.entity.AccountsEntity;
import com.example.entity.TransfersEntity;
import com.example.repository.AccountRepository;
import com.example.repository.TransferRepository;
import com.example.utils.ResponseUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TransferService {
    @PersistenceContext
    EntityManager em;

    @Inject
    TransferRepository transferRepository;

    @Inject
    AccountRepository accountRepository;

    public Response CreateTrasfer(TransferRequest tr){

        if(tr == null){
            return ResponseUtil.error("Cuerpo de la peticion erroneo", Response.Status.BAD_REQUEST);
        }
        if(tr.originAccount== null || tr.originAccount.isEmpty()){
            return ResponseUtil.error("Error con la cuenta de origen", Response.Status.BAD_REQUEST);
        }
        if(tr.destinationAccount== null || tr.destinationAccount.isEmpty()){
            return ResponseUtil.error("Ingrerse una cuenta destinataria", Response.Status.BAD_REQUEST);
        }
        if(tr.amount == 0){
            return ResponseUtil.error("El monto debe ser mayor a 0", Response.Status.BAD_REQUEST);
        }
        if(tr.type == null){
            return ResponseUtil.error("El tipo de transferencia es obligatorio", Response.Status.BAD_REQUEST);
        }

        boolean viableTransdfer = accountRepository.isViable(tr.originAccount, tr.amount);

        if(!viableTransdfer){
            return ResponseUtil.error("La cuenta no posee los suficientes fondos", Response.Status.BAD_REQUEST);
        }

        AccountsEntity OA = accountRepository.findByNA(tr.originAccount);
        AccountsEntity DA = accountRepository.findByNA(tr.destinationAccount);

            OA.setBalance(OA.getBalance() - tr.amount);
            DA.setBalance(DA.getBalance() + tr.amount);
            accountRepository.update(OA);
            accountRepository.update(DA);



        transferRepository.createTransfer(tr, OA,DA);

        return ResponseUtil.ok("Transferencia hecha con exito", Response.Status.CREATED);
    }

    public Response listar(){
        List<TransferResponse> lista = em.createQuery("select e from TransfersEntity e", TransfersEntity.class)
                .getResultList().stream().map(this::transferResponse).toList();
        return ResponseUtil.ok("Listado", lista);
    }

    public Response transferFind(UUID idUser){
        List<TransferResponse> lista = em.createQuery("select e from TransfersEntity e where e.originAccount.user.idUser = :idUser", TransfersEntity.class)
                .setParameter("idUser", idUser)
                .getResultList().stream().map(this::transferResponse).toList();

        if(lista.isEmpty()){
            return ResponseUtil.ok("Aun no hay transferencias", null);
        }
        return ResponseUtil.ok("Listado", lista);
    }

    public Response transferByNAccount(UUID idUser, String NAccount){
        List<TransferResponse> lista = em.createQuery("select e from TransfersEntity e where e.originAccount.user.idUser = :idUser AND e.originAccount.accountNumber =:NAccount or e.destinationAccount.accountNumber = :NAccount", TransfersEntity.class)
                .setParameter("idUser", idUser)
                .setParameter("NAccount", NAccount)
                .getResultList().stream().map(this::transferResponse).toList();

        if(lista.isEmpty()){
            return ResponseUtil.ok("Aun no hay transferencias", null);
        }
        return ResponseUtil.ok("Listado", lista);
    }

    private TransferResponse transferResponse(TransfersEntity te){
        TransferResponse tr = new TransferResponse();
        tr.amount = te.getAmount();
        tr.description = te.getDescription();
        tr.referenceCode = te.getReferenceCode();
        tr.status = te.getStatus();
        tr.type = te.getType();
        tr.originAccount = te.getOriginAccount().getAccountNumber();
        tr.destinationAccount = te.getDestinationAccount().getAccountNumber();
        tr.destinatario = te.getDestinationAccount().getUser().getFirstName() +" "+ te.getDestinationAccount().getUser().getLastName();
        tr.emisor = te.getOriginAccount().getUser().getFirstName() +" "+ te.getOriginAccount().getUser().getLastName();
        tr.createAt = te.getCreateAt();
        tr.processedAt = te.getProcessedAt();

        return tr;
    }


}
