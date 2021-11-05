package com.boba.bobabuddy.core.usecase.port.userport;

import com.boba.bobabuddy.core.entity.User;

public interface ILoginUser {
    boolean logIn(User user, String password);
}
