package com.boba.bobabuddy.core.usecase.auth;

import com.boba.bobabuddy.core.usecase.exceptions.FirebaseTokenInvalidException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import org.apache.commons.lang3.StringUtils;


public class FirebaseParser {
	public static FirebaseToken parseToken(String idToken) {
		if (StringUtils.isBlank(idToken)) {
			throw new IllegalArgumentException("FirebaseTokenBlank");
		}
		try {
			return FirebaseAuth.getInstance().verifyIdToken(idToken);

		} catch (Exception e) {
			throw new FirebaseTokenInvalidException(e.getMessage());
		}
	}
}
