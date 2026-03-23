package com.example.controller;

import com.example.dto.transfer.TransferRequest;
import com.example.service.TransferService;
import com.example.utils.ResponseUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

@Path("/transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransferController {

    @Inject
    TransferService transferService;
    @Inject
    JsonWebToken jwt;


    @POST
    public Response createTransfer(TransferRequest tr){
        return transferService.CreateTrasfer(tr);
    }

    @GET
    @Path("/list")
    public Response listar(){
        return transferService.listar();
    }

    @GET
    public Response myTransfers(){
        UUID idUser = null;
        if (jwt.getName() != null){
            idUser = UUID.fromString(jwt.getName());
        }

        return transferService.transferFind(idUser);
    }

    @GET
    @Path("/{NAccount}")
    public Response transferByAccount(@PathParam("NAccount") String NAccount){
        UUID idUser = null;
        if (jwt.getName() != null){
            idUser = UUID.fromString(jwt.getName());
        }
        return transferService.transferByNAccount(idUser,NAccount);
    }


}
