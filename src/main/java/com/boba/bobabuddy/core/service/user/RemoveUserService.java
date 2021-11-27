package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

/**
 * Usecase Input Boundary
 */
public interface RemoveUserService {
    void removeByEmail(String email) throws ResourceNotFoundException;
}
