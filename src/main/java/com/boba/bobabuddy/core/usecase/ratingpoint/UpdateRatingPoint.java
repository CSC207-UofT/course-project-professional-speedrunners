package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IUpdateRatingPoint;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * This is the usecase responsible for updating existing RatingPoint entities in the system.
 */
@Service
@Transactional
public class UpdateRatingPoint implements IUpdateRatingPoint {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;

    /**
     * Constructor for the UpdateRatingPoint usecase.
     *
     * @param repo the RatingJpaRepository with RatingPoint entities to be updated
     */
    @Autowired
    public UpdateRatingPoint(RatingJpaRepository repo) {
        this.repo = repo;
    }

    /**
     * Update the rating of a RatingPoint.
     *
     * @param id        the UUID of the RatingPoint to be updated
     * @param newRating the new rating of the RatingPoint
     * @return the updated RatingPoint
     * @throws ResourceNotFoundException if no RatingPoint with the given UUID is found
     * @throws IllegalArgumentException  if the new rating is not 1 or 0
     */
    @Override
    public RatingPoint updateRatingPointRating(UUID id, int newRating)
            throws ResourceNotFoundException, IllegalArgumentException {
        if (newRating != 0 && newRating != 1) {
            throw new IllegalArgumentException("Rating must be 0 or 1");
        }

        Optional<RatingPoint> ratingPoint = repo.findById(id);
        if (ratingPoint.isPresent()) {
            RatingPoint updatedRatingPoint = ratingPoint.get();
            updatedRatingPoint.setRating(newRating);
            return repo.save(updatedRatingPoint);
        }
        throw new ResourceNotFoundException("No such rating");
    }
}
