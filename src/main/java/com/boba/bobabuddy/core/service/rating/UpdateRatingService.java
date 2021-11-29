package com.boba.bobabuddy.core.service.rating;

import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface UpdateRatingService {
    /**
     * Update the rating of a Rating.
     *
     * @param id        the UUID of the Rating to be updated
     * @param newRating the new rating of the Rating
     * @return the updated Rating
     * @throws ResourceNotFoundException if no Rating with the given UUID is found
     * @throws IllegalArgumentException  if the new rating is not 1 or 0
     */
    Rating updateRating(UUID id, int newRating) throws IllegalArgumentException, ResourceNotFoundException;
}
