package com.boba.bobabuddy.core.usecase.exceptions;

public class NoSuchItemException extends Exception {
    public NoSuchItemException(String msg, Throwable err) {
        super(msg, err);
    }
}
