package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface FindUserService {

    /**
     * Find user by its id
     * @param id UUID of the user
     * @return the user if it was found.
     * @throws ResourceNotFoundException Thrown when no user with matching email exist in the database
     */
    User findById(UUID id) throws ResourceNotFoundException;

    /**
     * Find user by its email
     * @param email email of the user
     * @return the user if it was found.
     * @throws ResourceNotFoundException Thrown when no user with matching email exist in the database
     */
    User findByEmail(String email) throws ResourceNotFoundException;

    /**
     * Find all user that exist in the database
     * @return list of all user in the database, or an empty list if no user exist in the database
     */
    List<User> findAll();

    /**
     * Find user by its name. Have to be fully matching.
     * @param name name to be matched
     * @return user with matching name. Or an empty list if no such user exist
     */
    List<User> findByName(String name);

    /**
     * Check if a user with the associated email already exists in the database
     * @param email the email to check
     * @return true if such user exist
     */
    boolean userExistenceCheck(String email);

    /**
     * Find user that has a matching rating object
     * @param id id of the Rating entity
     * @return user if such user exist
     * @throws ResourceNotFoundException thrown when no user possessing this rating exist
     */
    User findByRating(UUID id) throws ResourceNotFoundException;
}
