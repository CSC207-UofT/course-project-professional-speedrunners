package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IFindRatingPoint;
import com.boba.bobabuddy.core.usecase.ratingpoint.exceptions.RatingPointNotFoundException;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This is the usecase responsible for finding existing RatingPoint entities in the system.
 */

public class FindRatingPoint implements IFindRatingPoint {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;

    /**
     * Constructor for the FindRatingPoint usecase.
     * @param repo the RatingJpaRepository to be searched for RatingPoint entities
     */
    public FindRatingPoint(RatingJpaRepository repo) {
        this.repo = repo;
    }

    /**
     * Find every RatingPoint associated with a RatableObject by its UUID.
     * @param id the UUID of the RatableObject
     * @return a list of every RatingPoint associated the RatableObject
     */
    @Override
    public List<RatingPoint> findByRatableObject(UUID id) {
        return repo.findByRatableObject_id(id);
    }

    /**
     * Find every RatingPoint associated with a User by its UUID.
     * @param id the UUID of the User
     * @return a list of every RatingPoint associated the User
     */
    @Override
    public List<RatingPoint> findByUser(UUID id) {
        return repo.findByUser_id(id);
    }

    /**
     * Find the RatingPoint entity with the given UUID.
     * @param id the UUID of the RatingPoint
     * @return the RatingPoint with the UUID
     * @throws RatingPointNotFoundException if no RatingPoint with the given UUID is found
     */
    @Override
    public RatingPoint findById(UUID id) throws RatingPointNotFoundException {
        Optional<RatingPoint> ratingPoint = repo.findById(id);
        if (ratingPoint.isPresent()) {
            return ratingPoint.get();
        }
        throw new RatingPointNotFoundException();
    }
}
