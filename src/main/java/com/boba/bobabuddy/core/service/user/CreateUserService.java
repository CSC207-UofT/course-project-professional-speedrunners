package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.data.dto.UserDto;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;

/**
 * Usecase Input Boundary
 */
public interface CreateUserService {
    /***
     * Persist a new user to the database
     * @param user User to be persisted to the database
     * @return the persisted user entity
     * @throws DuplicateResourceException thrown when user with the same email exists in the database
     */
    User create(UserDto user) throws DuplicateResourceException;
}
