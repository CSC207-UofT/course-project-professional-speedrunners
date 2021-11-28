package com.boba.bobabuddy.core.service.rating;

import com.boba.bobabuddy.core.data.dto.RatingDto;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface CreateRatingService {
    /**
     * Persist a new rating to the database and construct relevant bidirectional associations
     *
     * @param rating      new rating to be persisted
     * @param ratableType
     * @param ratableId   id of the associated ratable object
     * @param email       email of the associated user
     * @return the persisted rating entity
     * @throws ResourceNotFoundException  Thrown when either the ratable object or the user was not found
     * @throws DuplicateResourceException Thrown when this rating already exist in either the user or the ratable object
     * @throws IllegalArgumentException   Thrown when the rating is not 0 or 1
     */
    Rating create(RatingDto rating, String ratableType, UUID ratableId, String email) throws ResourceNotFoundException, DuplicateResourceException;
}
