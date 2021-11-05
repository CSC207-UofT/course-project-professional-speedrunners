package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IRemoveRatingPoint;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * This is the usecase responsible for removing RatingPoint entities from the system.
 */
@Service
@Transactional
public class RemoveRatingPoint implements IRemoveRatingPoint {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;

    /**
     * Constructor for the RemoveRatingPoint usecase.
     *
     * @param repo the RatingJpaRepository to remove RatingPoint entities from
     */
    @Autowired
    public RemoveRatingPoint(RatingJpaRepository repo) {
        this.repo = repo;
    }

    /**
     * Remove the RatingPoint entity with the given UUID.
     * TODO: make sure the RatingPoint is also removed from its associated User and RatableObject
     *
     * @param id the UUID of the RatingPoint to be removed
     * @return the removed RatingPoint entity
     */
    @Override
    public RatingPoint removeById(UUID id) throws ResourceNotFoundException {
        Optional<RatingPoint> point = repo.removeById(id);
        if (point.isPresent()) {
            return point.get();
        } else throw new ResourceNotFoundException("resource not found", new Exception());

    }
}
