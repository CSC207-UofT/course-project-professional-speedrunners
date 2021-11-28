package com.boba.bobabuddy.core.exceptions;

import lombok.experimental.StandardException;

/***
 * Thrown when a PUT request provided an entity patch that has a different primary key than the patch target.
 */
@StandardException
public class DifferentResourceException extends RuntimeException {
    public DifferentResourceException(String message) {
        super(message);
    }
}
