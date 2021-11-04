package com.boba.bobabuddy.core.usecase.port.ratingpointport;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.UUID;

public interface IRemoveRatingPoint {
    RatingPoint removeById(UUID id) throws ResourceNotFoundException;
}
