package com.example.controller;

import com.example.constan.UserRole;
import com.example.dto.account.AccountRequest;
import com.example.service.AccountService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountController {

    @Inject
    AccountService accountService;
    @Inject
    JsonWebToken jwt;

    @POST
    @RolesAllowed({"ADMIN","CLIENT"})
    public Response createAccount(AccountRequest ar){
        ar.userRole = jwt.getGroups()
                .stream()
                .findFirst()
                .map(role -> UserRole.valueOf(role.toUpperCase()))
                .orElse(ar.userRole);

        ar.idUser = (ar.idUser==null || ar.idUser.toString().isEmpty()) ? UUID.fromString(jwt.getName()): ar.idUser;
        return  accountService.createAccount(ar);
    }

    @GET
    @Path("/list")
    public Response listAccounts(){
        return accountService.listar();
    }

    @GET
    @RolesAllowed({"ADMIN","CLIENT"})
    public Response myAccount(){
        UUID idUser = null;
        if (jwt.getName() != null){
            idUser = UUID.fromString(jwt.getName());
        }
        return  accountService.accountFind(idUser);
    }

    @GET
    @RolesAllowed({"ADMIN","CLIENT"})
    @Path("/{NAccount}")
    public Response accountByNAccount(@PathParam("NAccount") String NAccount){
        return accountService.AccountByN(NAccount);
    }
}
