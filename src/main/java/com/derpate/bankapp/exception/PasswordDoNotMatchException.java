package com.derpate.bankapp.exception;

public class PasswordDoNotMatchException extends Exception {

    public PasswordDoNotMatchException(String msg) {
        super(msg);
    }
}
