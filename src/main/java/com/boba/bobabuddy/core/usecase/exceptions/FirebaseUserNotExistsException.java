package com.boba.bobabuddy.core.usecase.exceptions;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class FirebaseUserNotExistsException extends AuthenticationCredentialsNotFoundException {

	public FirebaseUserNotExistsException() {
		super("User Not Found");
	}

}
