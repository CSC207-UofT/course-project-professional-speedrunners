package com.boba.bobabuddy.core.service.auth;

import com.google.firebase.auth.FirebaseToken;

/**
 * FirebaseService used to generate FirebaseToken class, which contains user information form firebase auth server
 */
public interface FirebaseService {

    FirebaseToken parseToken(String idToken);

}
