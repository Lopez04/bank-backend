package com.example.utils;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ResponseValidator implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {


        StringBuilder sb = new StringBuilder();
        exception.getConstraintViolations().forEach(cv ->
                sb.append(cv.getMessage()).append("; ")
        );

        return ResponseUtil.error(sb.toString(), Response.Status.BAD_REQUEST);
    }
}