package com.example.controller;

import com.example.dto.auth.LoginRequest;
import com.example.dto.user.UserRequest;
import com.example.service.AuthService;
import com.example.service.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    AuthService authService;

    @Inject
    UserService userService;


    @POST
    @Path("/login")
    public Response login(LoginRequest lr){
        return authService.login(lr);
    }

    @POST
    @Path("/register")
    public Response userCreate(@Valid UserRequest ur){
        return userService.create(ur);
    }

}
