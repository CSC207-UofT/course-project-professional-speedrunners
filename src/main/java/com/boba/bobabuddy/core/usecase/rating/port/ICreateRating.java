package com.boba.bobabuddy.core.usecase.rating.port;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.UUID;
/**
 * Usecase Input Boundary
 */
public interface ICreateRating {
    Rating create(Rating rating, UUID ratableId, String email) throws ResourceNotFoundException, DuplicateResourceException;
}
