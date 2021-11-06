package com.boba.bobabuddy.core.usecase.user.port;

import com.boba.bobabuddy.core.entity.User;

public interface ILoginUser {
    boolean logIn(User user, String password);
}
