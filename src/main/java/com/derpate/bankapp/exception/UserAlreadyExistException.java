package com.derpate.bankapp.exception;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(String msg) {
        super(msg);
    }
}
