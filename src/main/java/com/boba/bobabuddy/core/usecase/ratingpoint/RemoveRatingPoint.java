package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IRemoveRatingPoint;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;

import java.util.UUID;

/**
 * This is the usecase responsible for removing RatingPoint entities from the system.
 */

public class RemoveRatingPoint implements IRemoveRatingPoint {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;

    /**
     * Constructor for the RemoveRatingPoint usecase.
     * @param repo the RatingJpaRepository to remove RatingPoint entities from
     */
    public RemoveRatingPoint(RatingJpaRepository repo) {
        this.repo = repo;
    }

    /**
     * Remove the RatingPoint entity with the given UUID.
     * TODO: make sure the RatingPoint is also removed from its associated User and RatableObject
     * @param id the UUID of the RatingPoint to be removed
     * @return the removed RatingPoint entity
     */
    @Override
    public RatingPoint removeById(UUID id) {
        return repo.removeById(id);
    }
}
