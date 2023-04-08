package com.derpate.bankapp.exception;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException(String msg) {
        super(msg);
    }
}
