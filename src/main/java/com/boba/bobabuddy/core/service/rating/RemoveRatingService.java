package com.boba.bobabuddy.core.service.rating;

import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface RemoveRatingService {
    /**
     * Remove the Rating entity with the given UUID.
     *
     * @param id the UUID of the Rating to be removed
     */
    void removeById(UUID id) throws ResourceNotFoundException;
}
