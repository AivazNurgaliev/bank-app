package com.derpate.bankapp.exception;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
