package com.boba.bobabuddy.core.usecase.rating;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.ratableobject.UpdateRatable;
import com.boba.bobabuddy.core.usecase.ratableobject.port.IUpdateRatable;
import com.boba.bobabuddy.core.usecase.rating.port.IFindRating;
import com.boba.bobabuddy.core.usecase.rating.port.IUpdateRating;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * This is the usecase responsible for updating existing Rating entities in the system.
 */
@Service
@Transactional
public class UpdateRating implements IUpdateRating {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;
    private final IFindRating findRating;
    private final IUpdateRatable updateRatable;

    /**
     * Constructor for the UpdateRating usecase.
     *
     * @param repo the RatingJpaRepository with Rating entities to be updated
     * @param findRating FindRating usecase to find the rating to be updated
     * @param updateRatable the usecase to update RatableObjects
     */
    @Autowired
    public UpdateRating(RatingJpaRepository repo, IFindRating findRating, IUpdateRatable updateRatable) {
        this.repo = repo;
        this.findRating = findRating;
        this.updateRatable = updateRatable;
    }

    /**
     * Update the rating of a Rating.
     *
     * @param id        the UUID of the Rating to be updated
     * @param newRating the new rating of the Rating
     * @return the updated Rating
     * @throws ResourceNotFoundException if no Rating with the given UUID is found
     * @throws IllegalArgumentException  if the new rating is not 1 or 0
     */
    @Override
    public Rating updateRating(UUID id, int newRating)
            throws ResourceNotFoundException, IllegalArgumentException {
        if (newRating != 0 && newRating != 1) {
            throw new IllegalArgumentException("Rating must be 0 or 1");
        }

        Rating rating = findRating.findById(id);
        RatableObject ratableObject = rating.getRatableObject();
        int oldRating = rating.getRating();
        updateRatable.updateRating(ratableObject, rating, oldRating, newRating);
        rating.setRating(newRating);
        return repo.save(rating);
    }
}
