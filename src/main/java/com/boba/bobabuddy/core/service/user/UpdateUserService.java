package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.data.dto.UserDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;

import java.io.IOException;
import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface UpdateUserService {
    User updateUser(User userToUpdate, UserDto newUser) throws DifferentResourceException;

    User addRating(User userToUpdate, Rating rating) throws DuplicateResourceException;

    /**
     * Update a user's image
     * @param id id of the user
     * @param imageURL URL of the image
     * @return updated user
     * @throws IOException is thrown when user's image cannot be updated
     */
    User updateUserImage(UUID id, String imageURL) throws IOException;
}
