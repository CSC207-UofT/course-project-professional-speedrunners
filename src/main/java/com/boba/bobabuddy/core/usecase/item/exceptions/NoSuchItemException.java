package com.boba.bobabuddy.core.usecase.item.exceptions;

public class NoSuchItemException extends Exception {
    public NoSuchItemException(String msg, Throwable err) {
        super(msg, err);
    }
}
