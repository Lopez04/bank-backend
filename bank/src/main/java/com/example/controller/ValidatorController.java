package com.example.controller;

import com.example.utils.ResponseUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("validator")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
public class ValidatorController {

    @GET
    @RolesAllowed({ "ADMIN",
            "CLIENT",
            "MERCHANT",
            "LOAN_OFFICER",
            "PAYROLL_MANAGER"})
    public Response validationToken(){
        return ResponseUtil.ok("Validacion correcta",null);
    }
}
