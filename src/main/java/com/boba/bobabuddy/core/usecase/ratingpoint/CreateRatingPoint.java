package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.ICreateRatingPoint;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;

/**
 * This is the usecase responsible for creating new RatingPoint entities and adding them to the system.
 */

public class CreateRatingPoint implements ICreateRatingPoint {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;

    /**
     * Constructor for the CreateRatingPoint usecase.
     * @param repo a RatingJpaRepository for storing the new RatingPoint objects
     */
    public CreateRatingPoint(RatingJpaRepository repo) {
        this.repo = repo;
    }

    /**
     * Save a RatingPoint to the database.
     * TODO: add the new RatingPoint to the associated User and RatableObject
     * @param ratingPoint the RatingPoint to be saved in the database
     * @return the saved RatingPoint
     */
    @Override
    public RatingPoint create(RatingPoint ratingPoint) {
        return repo.save(ratingPoint);
    }
}
