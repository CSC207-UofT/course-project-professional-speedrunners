package com.boba.bobabuddy.core.usecase.user.port;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;

public interface IUpdateUser {
    User updateUser(User userToUpdate, User newUser) throws DifferentResourceException;

    User addRating(User userToUpdate, Rating rating) throws DuplicateResourceException;
}
