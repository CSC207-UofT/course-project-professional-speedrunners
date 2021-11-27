package com.boba.bobabuddy.core.service.auth;

import com.google.firebase.auth.FirebaseToken;

public interface FirebaseService {

    FirebaseToken parseToken(String idToken);

}
