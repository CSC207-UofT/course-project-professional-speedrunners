package com.boba.bobabuddy.core.usecase.port.ratingpointport;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.ratingpoint.exceptions.RatingPointNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IFindRatingPoint {
    List<RatingPoint> findByRatableObject(UUID id);

    List<RatingPoint> findByUser(String email);

    RatingPoint findById(UUID id) throws RatingPointNotFoundException;
}
