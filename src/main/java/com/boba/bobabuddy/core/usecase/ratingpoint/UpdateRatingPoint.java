package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IUpdateRatingPoint;
import com.boba.bobabuddy.core.usecase.ratingpoint.exceptions.InvalidRatingException;
import com.boba.bobabuddy.core.usecase.ratingpoint.exceptions.RatingPointNotFoundException;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;

import java.util.Optional;
import java.util.UUID;

public class UpdateRatingPoint implements IUpdateRatingPoint {

    private final RatingJpaRepository repo;

    public UpdateRatingPoint(RatingJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public RatingPoint updateRatingPointRating(UUID id, int newRating)
            throws RatingPointNotFoundException, InvalidRatingException {
        if (newRating != 0 && newRating != 1) {
            throw new InvalidRatingException();
        }
        Optional<RatingPoint> ratingPoint = repo.findById(id);
        if (ratingPoint.isPresent()) {
            ratingPoint.get().setRating(newRating);
            return ratingPoint.get();
        }
        throw new RatingPointNotFoundException();
    }
}
