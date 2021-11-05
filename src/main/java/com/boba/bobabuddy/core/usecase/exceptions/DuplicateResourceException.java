package com.boba.bobabuddy.core.usecase.exceptions;

public class DuplicateResourceException extends Throwable {
    public DuplicateResourceException(String msg, Throwable cause){super(msg, cause);}
    public DuplicateResourceException(String msg){super(msg);}
}
