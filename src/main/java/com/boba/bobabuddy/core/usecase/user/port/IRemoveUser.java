package com.boba.bobabuddy.core.usecase.user.port;

import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
/**
 * Usecase Input Boundary
 */
public interface IRemoveUser {
    void removeByEmail(String email) throws ResourceNotFoundException;
}
