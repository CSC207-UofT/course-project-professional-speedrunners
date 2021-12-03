package com.boba.bobabuddy.core.usecase.ratableobject.port;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
/**
 * Usecase Input Boundary
 */
public interface IUpdateRatable {
    RatableObject addRating(RatableObject ratable, Rating newRating) throws DuplicateResourceException;

    RatableObject removeRating(RatableObject ratable, Rating rating) throws ResourceNotFoundException;

    RatableObject updateRating(RatableObject ratable, Rating rating, int oldRating, int newRating)
            throws ResourceNotFoundException;
}
