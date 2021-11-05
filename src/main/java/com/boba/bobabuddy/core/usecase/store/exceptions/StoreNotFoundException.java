package com.boba.bobabuddy.core.usecase.store.exceptions;

public class StoreNotFoundException extends Exception {
    public StoreNotFoundException(String msg, Throwable err) {
        super(msg, err);
    }
}
