package com.boba.bobabuddy.core.usecase.store.exceptions;

public class NoSuchStoreException extends Exception {
    public NoSuchStoreException(String msg, Throwable err) {
        super(msg, err);
    }
}
