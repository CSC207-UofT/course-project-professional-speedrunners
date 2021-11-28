package com.boba.bobabuddy.core.service.rating.impl;

import com.boba.bobabuddy.core.data.dao.RatingJpaRepository;
import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.ratableobject.FindRatableService;
import com.boba.bobabuddy.core.service.ratableobject.UpdateRatableService;
import com.boba.bobabuddy.core.service.rating.FindRatingService;
import com.boba.bobabuddy.core.service.rating.UpdateRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This is the usecase responsible for updating existing Rating entities in the system.
 */
@Service("UpdateRatingService")
@Transactional
public class UpdateRatingServiceImpl implements UpdateRatingService {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;
    private final FindRatingService findRating;
    private final FindRatableService findRatable;
    private final UpdateRatableService updateRatable;

    /**
     * Constructor for the UpdateRating usecase.
     *
     * @param repo          the RatingJpaRepository with Rating entities to be updated
     * @param findRating    FindRating usecase to find the rating to be updated
     * @param findRatable
     * @param updateRatable the usecase to update RatableObjects
     */
    @Autowired
    public UpdateRatingServiceImpl(RatingJpaRepository repo, FindRatingService findRating, FindRatableService findRatable, UpdateRatableService updateRatable) {
        this.repo = repo;
        this.findRating = findRating;
        this.findRatable = findRatable;
        this.updateRatable = updateRatable;
    }


    @Override
    public Rating updateRating(UUID id, int newRating)
            throws ResourceNotFoundException, IllegalArgumentException {
        if (newRating != 0 && newRating != 1) {
            throw new IllegalArgumentException("Rating must be 0 or 1");
        }

        Rating rating = findRating.findById(id);
        RatableObject ratableObject = findRatable.findByRating(rating.getId());
        int oldRating = rating.getRating();
        updateRatable.updateRating(ratableObject, rating, oldRating, newRating);
        rating.setRating(newRating);
        return repo.save(rating);
    }
}
