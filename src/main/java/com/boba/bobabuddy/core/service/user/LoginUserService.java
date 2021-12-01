package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.domain.User;

/**
 * Usecase Input Boundary
 */
public interface LoginUserService {
    boolean logIn(User user, String password);
}
