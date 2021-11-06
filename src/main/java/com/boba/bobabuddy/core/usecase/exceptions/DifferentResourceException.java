package com.boba.bobabuddy.core.usecase.exceptions;

/***
 * Thrown when a PUT request provided an entity patch that has a different primary key than the patch target.
 */
public class DifferentResourceException extends Throwable {
    public DifferentResourceException(String msg, Exception e) {
        super(msg, e);
    }

    public DifferentResourceException(String msg) {
        super(msg);
    }
}
