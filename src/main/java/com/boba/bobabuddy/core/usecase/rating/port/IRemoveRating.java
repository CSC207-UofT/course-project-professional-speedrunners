package com.boba.bobabuddy.core.usecase.rating.port;

import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.UUID;
/**
 * Usecase Input Boundary
 */
public interface IRemoveRating {
    void removeById(UUID id) throws ResourceNotFoundException;
}