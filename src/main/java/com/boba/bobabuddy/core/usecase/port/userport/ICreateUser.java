package com.boba.bobabuddy.core.usecase.port.userport;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;

public interface ICreateUser {
    User create(User user) throws DuplicateResourceException;
}
