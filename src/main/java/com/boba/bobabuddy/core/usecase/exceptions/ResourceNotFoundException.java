package com.boba.bobabuddy.core.usecase.exceptions;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String msg, Throwable err) {
        super(msg, err);
    }
    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}
