package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.data.dto.UserDto;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;

import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface UpdateUserService {
    /**
     * Update user fields and save it to the database
     * If a corresponding data was null in newUser, that field will not be modified.
     *
     * @param email   old user data
     * @param newUser user DTO containing new data and matching id
     * @return saved user entity
     * @throws DifferentResourceException when email and newUser have different email
     */
    User updateUser(String email, UserDto newUser) throws DifferentResourceException;
    /**
     * Internal usecase for creating of ratings
     * used for adding rating pointer to the user object during rating creation
     *
     * @param userToUpdate user to add rating to
     * @param rating       the rating
     * @return the updated User
     * @throws DuplicateResourceException when same rating already exist in user
     */
    User addRating(User userToUpdate, Rating rating) throws DuplicateResourceException;

    /**
     * Update a user's image
     * @param id id of the user
     * @param imageURL URL of the image
     * @return updated user
     */
    User updateUserImage(UUID id, String imageURL);
}
