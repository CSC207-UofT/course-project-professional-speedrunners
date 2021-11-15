package com.boba.bobabuddy.core.usecase.user.port;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.infrastructure.dto.UserDto;

public interface IUpdateUser {
    User updateUser(User userToUpdate, UserDto newUser) throws DifferentResourceException;

    User addRating(User userToUpdate, Rating rating) throws DuplicateResourceException;
}
