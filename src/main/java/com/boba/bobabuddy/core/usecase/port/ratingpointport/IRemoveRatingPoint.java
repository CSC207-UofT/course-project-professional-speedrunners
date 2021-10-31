package com.boba.bobabuddy.core.usecase.port.ratingpointport;

import com.boba.bobabuddy.core.entity.RatingPoint;

import java.util.UUID;

public interface IRemoveRatingPoint {
    RatingPoint removeById(UUID id);
}
