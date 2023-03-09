package com.derpate.bankapp.controller.handler;

import com.derpate.bankapp.exception.ErrorResponse;
import com.derpate.bankapp.exception.PasswordDoNotMatchException;
import com.derpate.bankapp.exception.UserAlreadyExistException;
import com.derpate.bankapp.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistException e, WebRequest request) {
        String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), requestURI);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e, WebRequest request) {
        String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), requestURI);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordDoNotMatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordDoNotMatchException(Exception e, WebRequest request) {
        String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), requestURI);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest request) {
        String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), requestURI);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
