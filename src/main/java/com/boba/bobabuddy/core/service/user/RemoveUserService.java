package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

/**
 * Usecase Input Boundary
 */
public interface RemoveUserService {

    /**
     * removes a user from database that has the matching email.
     * @param email id of the user
     * @throws ResourceNotFoundException when no user with the associated email was found
     */
    void removeByEmail(String email) throws ResourceNotFoundException;
}
