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

    /**
     * Find RatableObject by the rating object it contains
     * @param id uuid of rating
     * @return RatableObject matching the condition
     */
    RatableObject findByRating(UUID id);

    /**
     * Find RatableObject by both its type and its id
     * @param id uuid of rating
     * @param type type of the RatableObject, either Item or Store
     * @return RatableObject matching the condition
     */
    RatableObject findByTypeAndId(String type, UUID id);
}
