package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IFindRatingPoint;
import com.boba.bobabuddy.core.usecase.ratingpoint.exceptions.RatingPointNotFoundException;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FindRatingPoint implements IFindRatingPoint {

    private final RatingJpaRepository repo;

    public FindRatingPoint(RatingJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<RatingPoint> findByRatableObject(UUID id) {
        return repo.findByRatableObject_id(id);
    }

    @Override
    public List<RatingPoint> findByUser(UUID id) {
        return repo.findByUser_id(id);
    }

    @Override
    public RatingPoint findById(UUID id) throws RatingPointNotFoundException {
        Optional<RatingPoint> ratingPoint = repo.findById(id);
        if (ratingPoint.isPresent()) {
            return ratingPoint.get();
        }
        throw new RatingPointNotFoundException();
    }
}
