package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.ICreateRatingPoint;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is the usecase responsible for creating new RatingPoint entities and adding them to the system.
 */
@Service
@Transactional
public class CreateRatingPoint implements ICreateRatingPoint {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;

    /**
     * Constructor for the CreateRatingPoint usecase.
     *
     * @param repo a RatingJpaRepository for storing the new RatingPoint objects
     */
    @Autowired
    public CreateRatingPoint(RatingJpaRepository repo) {
        this.repo = repo;
    }

    /**
     * Save a RatingPoint to the database.
     * TODO: add the new RatingPoint to the associated User and RatableObject
     *
     * @param ratingPoint the RatingPoint to be saved in the database
     * @return the saved RatingPoint
     */
    @Override
    public RatingPoint create(RatingPoint ratingPoint) {
        return repo.save(ratingPoint);
    }
}
