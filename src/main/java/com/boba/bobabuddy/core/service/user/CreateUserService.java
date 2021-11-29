package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.data.dto.UserDto;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;

/**
 * Usecase Input Boundary
 */
public interface CreateUserService {
    User create(UserDto user) throws DuplicateResourceException;
}
