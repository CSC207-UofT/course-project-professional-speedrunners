package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IRemoveRatingPoint;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;

import java.util.UUID;

public class RemoveRatingPoint implements IRemoveRatingPoint {
    private final RatingJpaRepository repo;

    public RemoveRatingPoint(RatingJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public RatingPoint removeById(UUID id) {
        return repo.removeByRatingPoint_id(id);
    }
}
