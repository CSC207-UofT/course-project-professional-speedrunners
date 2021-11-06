package com.boba.bobabuddy.core.usecase.exceptions;

/***
 * Thrown when the requested resource was not found in the database.
 */
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String msg, Throwable err) {
        super(msg, err);
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}
