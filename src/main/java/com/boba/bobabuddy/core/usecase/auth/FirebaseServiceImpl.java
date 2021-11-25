package com.boba.bobabuddy.core.usecase.auth;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;


@Service
public class FirebaseServiceImpl implements FirebaseService {
	@Override
	public FirebaseToken parseToken(String firebaseToken) {
		return FirebaseParser.parseToken(firebaseToken);
	}
}
