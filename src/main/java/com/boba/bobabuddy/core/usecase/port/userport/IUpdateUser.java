package com.boba.bobabuddy.core.usecase.port.userport;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.user.exceptions.UserNotFoundException;

public interface IUpdateUser{
    User updateUser(User user) throws UserNotFoundException;
}
