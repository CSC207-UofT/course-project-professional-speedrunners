package com.boba.bobabuddy.core.usecase.port.ratingpointport;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.UUID;

public interface ICreateRatingPoint {
    RatingPoint create(RatingPoint ratingPoint, UUID ratableId, String email) throws ResourceNotFoundException, DuplicateResourceException;
}
