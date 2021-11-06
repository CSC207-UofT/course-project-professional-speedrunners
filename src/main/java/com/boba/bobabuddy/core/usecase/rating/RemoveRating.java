package com.boba.bobabuddy.core.usecase.rating;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.rating.port.IFindRating;
import com.boba.bobabuddy.core.usecase.rating.port.IRemoveRating;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This is the usecase responsible for removing Rating entities from the system.
 */
@Service
@Transactional
public class RemoveRating implements IRemoveRating {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;
    private final IFindRating findRating;

    /**
     * Constructor for the RemoveRating usecase.
     *
     * @param repo       the RatingJpaRepository to remove Rating entities from
     * @param findRating FindRating usecase to find the rating to be removed
     */
    @Autowired
    public RemoveRating(RatingJpaRepository repo, IFindRating findRating) {
        this.repo = repo;
        this.findRating = findRating;
    }

    /**
     * Remove the Rating entity with the given UUID.
     * TODO: NEED TESTING -- make sure the Rating is also removed from its associated User and RatableObject
     *
     * @param id the UUID of the Rating to be removed
     */
    @Override
    public void removeById(UUID id) throws ResourceNotFoundException {
        Rating rating = findRating.findById(id);
        repo.delete(rating);
    }
}
