package com.example.service;

import com.example.constan.AccountType;
import com.example.constan.UserRole;
import com.example.dto.account.AccounResponse;
import com.example.dto.account.AccountRequest;
import com.example.entity.AccountsEntity;
import com.example.entity.UsersEntity;
import com.example.repository.AccountRepository;
import com.example.repository.UserRepository;
import com.example.utils.ResponseUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class AccountService {

    @Inject
    AccountRepository accountRepository;
    @Inject
    UserRepository userRepository;

    public Response createAccount(AccountRequest ar){
        if(ar == null){
            return ResponseUtil.error("Cuerpo Invalido", Response.Status.BAD_REQUEST);
        }

        if (ar.type == null ){
            return ResponseUtil.error("Tipo de cuenta Invalido", Response.Status.BAD_REQUEST);
        }
        if (ar.currency == null || ar.currency.isEmpty()){
            ar.currency = "USD";
        }

        if(ar.userRole == UserRole.CLIENT && !(ar.type == AccountType.SAVINGS || ar.type==AccountType.CURRENT)){
            return ResponseUtil.error("No puedes crear este tipo de cuenta", Response.Status.BAD_REQUEST);
        };

        if(ar.userRole == UserRole.MERCHANT && !(ar.type == AccountType.BUSINESS || ar.type==AccountType.CURRENT)){
            return ResponseUtil.error("No puedes crear este tipo de cuenta", Response.Status.BAD_REQUEST);
        };

        if(ar.userRole == UserRole.LOAN_OFFICER && !(ar.type == AccountType.CREDIT)){
            return ResponseUtil.error("No puedes crear este tipo de cuenta", Response.Status.BAD_REQUEST);
        };
        if(ar.userRole == UserRole.PAYROLL_MANAGER && !(ar.type == AccountType.PAYROLL)){
            return ResponseUtil.error("No puedes crear este tipo de cuenta", Response.Status.BAD_REQUEST);
        };



        UsersEntity ue = userRepository.find(ar.idUser);
        AccounResponse ars = new AccounResponse();
        try {
            ars.accountNumber = accountRepository.create(ar, ue);

            return ResponseUtil.ok("Cuenta creada con exito", ars);
        }catch (Exception error){
            return ResponseUtil.error("ocurrio un error inesperado intenta mas tarde", Response.Status.BAD_REQUEST);
        }

    }

    public Response listar(){
        List<AccounResponse> lista = accountRepository.findAll().stream().map(this::ResponseAccount).toList();

        return ResponseUtil.ok("listado", lista);
    }

    public Response accountFind(UUID idUser){

        if(idUser == null){
            return ResponseUtil.error("Token invalido", Response.Status.BAD_REQUEST);
        }

        List<AccounResponse> fa = accountRepository.AccountsUser(idUser).stream().map(this::ResponseAccount).toList();

        if(fa.isEmpty() ){
            return ResponseUtil.ok("aun no existe cuentas", null);
        }

        return ResponseUtil.ok("Cuenta encotrada", fa);
    }

    public Response AccountByN(String NAccount){

        if(NAccount.isEmpty()){
            return ResponseUtil.error("El numero de cuenta es requerido", Response.Status.BAD_REQUEST);
        }

        AccountsEntity ae = accountRepository.findByNA(NAccount);

        if(ae == null){
            return ResponseUtil.ok("cuenta no encontrada", null);
        }

        return ResponseUtil.ok("cuenta "+ NAccount, ResponseAccount(ae) );
    }

    private AccounResponse ResponseAccount(AccountsEntity ae){

        AccounResponse ar = new AccounResponse();
        ar.balance = ae.getBalance();
        ar.accountNumber = ae.getAccountNumber();
        ar.active = ae.getActive();
        ar.type = ae.getType();
        ar.owner = ae.getUser().getFirstName()+" "+ ae.getUser().getLastName();
        ar.currency = ae.getCurrency();
        ar.createAt = ae.getCreateAt();
        ar.updateAt = ae.getUpdateAt();

        return ar;
    }

}
