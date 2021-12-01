package com.boba.bobabuddy.core.exceptions;

import lombok.experimental.StandardException;

/***
 * Thrown when the requested resource was not found in the database.
 */
@StandardException
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
