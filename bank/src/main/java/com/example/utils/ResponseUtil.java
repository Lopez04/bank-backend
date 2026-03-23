package com.example.utils;

import com.example.dto.ResponseDTO;
import jakarta.ws.rs.core.Response;

public class ResponseUtil {
    public static <T> Response ok(String message, T data){
        return Response.status(Response.Status.OK)
                .entity(new ResponseDTO<>("Success",message, data))
                .build();
    }

    public static <T> Response error(String message, Response.Status status){
        return Response.status(status)
                .entity(new ResponseDTO<>("Error",message))
                .build();
    }
}
