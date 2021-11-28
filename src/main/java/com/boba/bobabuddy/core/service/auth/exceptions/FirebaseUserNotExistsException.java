package com.boba.bobabuddy.core.service.auth.exceptions;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class FirebaseUserNotExistsException extends AuthenticationCredentialsNotFoundException {

    public FirebaseUserNotExistsException() {
        super("User Not Found");
    }

    public FirebaseUserNotExistsException(Exception e) {
        super(e.getMessage(), e);
    }

}
