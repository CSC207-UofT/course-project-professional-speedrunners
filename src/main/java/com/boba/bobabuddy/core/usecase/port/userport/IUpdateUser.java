package com.boba.bobabuddy.core.usecase.port.userport;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;

public interface IUpdateUser {
    User updateUser(User userToUpdate, User newUser) throws DifferentResourceException;

    public User addRating(User userToUpdate, RatingPoint ratingPoint) throws DuplicateResourceException;
}
