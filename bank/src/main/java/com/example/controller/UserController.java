package com.example.controller;

import com.example.constan.UserRole;
import com.example.dto.user.UserRequest;
import com.example.service.UserService;
import com.example.utils.ResponseUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @Inject
    JsonWebToken jwt;


    @GET
    public Response userList(){
        return userService.listar();
    }

    @GET
    @Path("/private")
    @RolesAllowed({"ADMIN", "CLIENT"})
    public Response privado(){
        String name= jwt.getName();
        return ResponseUtil.ok("OK", name);
    }

    public Response createUser(UserRequest ur){
        ur.asignedRoler  = jwt.getGroups()
                .stream()
                .findFirst()
                .map(UserRole::valueOf)
                .orElse(null);

        return userService.create(ur);
    }
}
