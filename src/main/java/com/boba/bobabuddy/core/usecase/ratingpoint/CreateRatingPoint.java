package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.ICreateRatingPoint;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;

public class CreateRatingPoint implements ICreateRatingPoint {

    private final RatingJpaRepository repo;

    public CreateRatingPoint(RatingJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public RatingPoint create(RatingPoint ratingPoint) {
        return repo.save(ratingPoint);
    }
}
