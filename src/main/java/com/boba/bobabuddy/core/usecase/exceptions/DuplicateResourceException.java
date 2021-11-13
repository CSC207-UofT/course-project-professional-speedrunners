package com.boba.bobabuddy.core.usecase.exceptions;

/***
 * Thrown when attempting to POST a resource that has the same primary key
 */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DuplicateResourceException(String msg) {
        super(msg);
    }
}
