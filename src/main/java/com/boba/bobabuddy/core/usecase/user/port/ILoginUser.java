package com.boba.bobabuddy.core.usecase.user.port;

import com.boba.bobabuddy.core.entity.User;
/**
 * Usecase Input Boundary
 */
public interface ILoginUser {
    boolean logIn(User user, String password);
}
