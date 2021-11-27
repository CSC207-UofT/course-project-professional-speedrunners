package com.boba.bobabuddy.core.exceptions;

import lombok.experimental.StandardException;

/***
 * Thrown when attempting to POST a resource that has the same primary key
 */
@StandardException
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
