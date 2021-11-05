package com.boba.bobabuddy.core.usecase.port.ratingpointport;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.UUID;

public interface IUpdateRatingPoint {
    RatingPoint updateRatingPointRating(UUID id, int newRating) throws IllegalArgumentException, ResourceNotFoundException;
}
