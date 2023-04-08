package com.derpate.bankapp.controller.handler;

import com.derpate.bankapp.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class MoneyExceptionHandler {

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughMoneyException(NotEnoughMoneyException e, WebRequest request) {
        String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), requestURI);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMoneyValueException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMoneyValueException(InvalidMoneyValueException e, WebRequest request) {
        String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), requestURI);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOperationException(InvalidOperationException e, WebRequest request) {
        String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), requestURI);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
