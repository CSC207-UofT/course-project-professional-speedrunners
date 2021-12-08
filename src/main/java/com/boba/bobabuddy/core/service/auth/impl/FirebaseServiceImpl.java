package com.boba.bobabuddy.core.service.auth.impl;

import com.boba.bobabuddy.core.service.auth.FirebaseService;
import com.boba.bobabuddy.core.service.auth.exceptions.FirebaseTokenInvalidException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Part of the firebase auth integration that is responsible for checking the validity of idTokens passed in via http header
 * X-Authentication-Firebase
 *
 * Uses FirebaseAuth Admin SDK instance to perform the token check
 */

@Service("FirebaseService")
public class FirebaseServiceImpl implements FirebaseService {
    @Override
    public FirebaseToken parseToken(String idToken) {
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
