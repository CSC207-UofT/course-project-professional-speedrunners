package com.boba.bobabuddy.core.service.ratableobject;

import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

/**
 * Usecase Input Boundary
 */

public interface UpdateRatableService {

    /**
     * Adding a rating to a ratable object
     *
     * @param ratable   the ratable object to append rating to
     * @param newRating the rating to be appended
     * @return the mutated ratable object
     * @throws DuplicateResourceException thrown when the rating already exist within the object
     */
    RatableObject addRating(RatableObject ratable, Rating newRating) throws DuplicateResourceException;

    /**
     * removing a rating from a ratable object
     *
     * @param ratable the ratable object to be mutated
     * @param rating  the rating to be removed
     * @return the mutated ratable object
     * @throws ResourceNotFoundException thrown when the object does not contain the rating
     */
    RatableObject removeRating(RatableObject ratable, Rating rating) throws ResourceNotFoundException;

    /**
     * update the avgRating of a ratable object
     *
     * @param ratable   the ratable object to be mutated
     * @param rating    the updated rating
     * @param oldRating the old rating value
     * @param newRating the new rating value
     * @return the mutated ratable object
     * @throws ResourceNotFoundException thrown when the object does not contain the rating
     */
    RatableObject updateRating(RatableObject ratable, Rating rating, int oldRating, int newRating)
            throws ResourceNotFoundException;
}
