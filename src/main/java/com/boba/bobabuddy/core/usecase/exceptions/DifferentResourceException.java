package com.boba.bobabuddy.core.usecase.exceptions;

public class DifferentResourceException extends Throwable {
    public DifferentResourceException(String msg, Exception e) {
        super(msg, e);
    }

    public DifferentResourceException(String msg) {
        super(msg);
    }
}
