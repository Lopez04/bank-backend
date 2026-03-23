package com.example.service;

import com.example.constan.UserRole;
import com.example.dto.user.UserRequest;
import com.example.dto.user.UserResponse;
import com.example.entity.UsersEntity;
import com.example.repository.AccountRepository;
import com.example.repository.UserRepository;
import com.example.utils.ResponseUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;
    @Inject
    AccountRepository accountRepository;

    public Response create(UserRequest ur){
        if(ur == null){
            return ResponseUtil.error("el objeto es necesario", Response.Status.BAD_REQUEST);
        }
        if(userRepository.existEmail(ur.email)){
            return ResponseUtil.error("El correo ya existe", Response.Status.BAD_REQUEST);
        }
        if(ur.asignedRoler != UserRole.ADMIN && ur.role != UserRole.CLIENT){
            return ResponseUtil.error("No puedes crear este tipo de cuenta", Response.Status.BAD_REQUEST);
        };
        if (ur.email == null || ur.email.isEmpty()){
            return ResponseUtil.error("El correo es obligatorio", Response.Status.BAD_REQUEST);
        } else if (ur.lastName == null || ur.lastName.isEmpty()) {
            return ResponseUtil.error("El apellido es obligatorio", Response.Status.BAD_REQUEST);
        } else if (ur.firstName == null || ur.firstName.isEmpty()) {
            return ResponseUtil.error("El nombre es obligatorio", Response.Status.BAD_REQUEST);
        } else if (ur.role == null) {
            ur.role = UserRole.CLIENT;
        }  else if (ur.password == null || ur.password.isEmpty()) {
            return ResponseUtil.error("La contraseña es obligatoria", Response.Status.BAD_REQUEST);
        }

        String message = userRepository.createData(ur);
        return ResponseUtil.ok(message, ur);
    }

    public Response listar (){
        List<UserResponse> lista = userRepository.findAll().stream().map(this::userResponse).toList();
        return ResponseUtil.ok("usuarios encontrados", lista);
    }

    private UserResponse userResponse(UsersEntity ue){

        UserResponse ur = new UserResponse();
        ur.userName = ue.getUserName() != null ? ue.getUserName() : "";
        ur.firstName = ue.getFirstName() != null ? ue.getFirstName() : "";
        ur.lastName = ue.getLastName() != null ? ue.getLastName() : "";
        ur.role = ue.getRole() != null ? ue.getRole(): null ;
        ur.email = ue.getEmail() != null ? ue.getEmail() : "";
        ur.userAccounts = accountRepository.AccountListUser(ue.getIdUser())  ;
        ur.token = null;

        return ur;
    }


}
