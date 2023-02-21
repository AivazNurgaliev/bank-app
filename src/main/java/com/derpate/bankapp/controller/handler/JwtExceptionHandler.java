package com.derpate.bankapp.controller.handler;

import com.derpate.bankapp.exception.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class JwtExceptionHandler {
    // TODO: 21.02.2023 rename
/*    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleJwtRuntimeException(RuntimeException e, WebRequest request) throws IOException {
        String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, e.getMessage(), requestURI);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }*/

    @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ErrorResponse> handleJwtRuntimeException(RuntimeException e, WebRequest request) throws IOException {
        String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(), requestURI);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
