package com.boba.bobabuddy.core.usecase.user.port;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;

/**
 * Usecase Input Boundary
 */
public interface ICreateUser {
    User create(User user) throws DuplicateResourceException;
}
