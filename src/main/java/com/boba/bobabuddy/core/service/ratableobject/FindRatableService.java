package com.boba.bobabuddy.core.service.ratableobject;

import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface FindRatableService {
    /**
     * Find RatableObject by its uuid
     *
     * @param id uuid of the item
     * @return the Item if it was found.
     * @throws ResourceNotFoundException Thrown when no item with matching ID exist in the database
     */
    RatableObject findById(UUID id);

    RatableObject findByRating(UUID id);

    RatableObject findByTypeAndId(String type, UUID id);
}
