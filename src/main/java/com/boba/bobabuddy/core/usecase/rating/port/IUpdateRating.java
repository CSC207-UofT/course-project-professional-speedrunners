package com.boba.bobabuddy.core.usecase.rating.port;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.UUID;
/**
 * Usecase Input Boundary
 */
public interface IUpdateRating {
    Rating updateRating(UUID id, int newRating) throws IllegalArgumentException, ResourceNotFoundException;
}
