package com.boba.bobabuddy.core.usecase.port.ratingpointport;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IFindRatingPoint {
    Set<RatingPoint> findByRatableObject(UUID id) throws ResourceNotFoundException;

    List<RatingPoint> findByUser(String email);

    RatingPoint findById(UUID id) throws ResourceNotFoundException;
}
