package com.boba.bobabuddy.core.usecase.port.ratingpointport;

import com.boba.bobabuddy.core.entity.RatingPoint;

import java.util.UUID;

public interface ICreateRatingPoint {
    public RatingPoint create(RatingPoint ratingPoint, UUID ratableId, String email) throws Exception;
}
