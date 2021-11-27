package com.boba.bobabuddy.core.service.rating.impl;

import com.boba.bobabuddy.core.data.dao.RatingJpaRepository;
import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.ratableobject.FindRatableService;
import com.boba.bobabuddy.core.service.ratableobject.UpdateRatableService;
import com.boba.bobabuddy.core.service.rating.FindRatingService;
import com.boba.bobabuddy.core.service.rating.RemoveRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This is the usecase responsible for removing Rating entities from the system.
 */
@Service
@Transactional
public class RemoveRatingServiceImpl implements RemoveRatingService {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;
    private final FindRatingService findRating;
    private final FindRatableService findRatable;
    private final UpdateRatableService updateRatable;

    /**
     * Constructor for the RemoveRating usecase.
     *
     * @param repo          the RatingJpaRepository to remove Rating entities from
     * @param findRating    FindRating usecase to find the rating to be removed
     * @param findRatable
     * @param updateRatable usecase to update RatableObjects
     */
    @Autowired
    public RemoveRatingServiceImpl(RatingJpaRepository repo, FindRatingService findRating, FindRatableService findRatable, UpdateRatableService updateRatable) {
        this.repo = repo;
        this.findRating = findRating;
        this.findRatable = findRatable;
        this.updateRatable = updateRatable;
    }


    @Override
    public void removeById(UUID id) throws ResourceNotFoundException {
        Rating rating = findRating.findById(id);
        RatableObject ratableObject = findRatable.findByRating(rating.getId());
        updateRatable.removeRating(ratableObject, rating);
        repo.delete(rating);
    }
}
