package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.ratableport.IFindRatable;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IFindRatingPoint;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This is the usecase responsible for finding existing RatingPoint entities in the system.
 */
@Service
@Transactional
public class FindRatingPoint implements IFindRatingPoint {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;
    private final IFindRatable findRatable;

    /**
     * Constructor for the FindRatingPoint usecase.
     *
     * @param repo        the RatingJpaRepository to be searched for RatingPoint entities
     */
    @Autowired
    public FindRatingPoint(RatingJpaRepository repo, IFindRatable findRatable) {
        this.repo = repo;
        this.findRatable = findRatable;
    }

    /**
     * Find every RatingPoint associated with a RatableObject by its UUID.
     * TODO: consider moving this usecase to RatableObject since it stores its ratings
     *
     * @param id the UUID of the RatableObject
     * @return a list of every RatingPoi.nt associated with the RatableObject
     */
    @Override
    public Set<RatingPoint> findByRatableObject(UUID id) throws ResourceNotFoundException {
        return findRatable.findById(id).getRatings();
    }

    /**
     * Find every RatingPoint associated with a User by its email.
     * TODO: consider moving this usecase to User since it stores its ratings
     *
     * @param email the UUID of the User
     * @return a list of every RatingPoint associated with the User
     */
    @Override
    public List<RatingPoint> findByUser(String email) {
        return repo.findByUser_email(email);
    }

    /**
     * Find the RatingPoint entity with the given UUID.
     *
     * @param id the UUID of the RatingPoint
     * @return the RatingPoint with the UUID
     * @throws ResourceNotFoundException if no RatingPoint with the given UUID is found
     */
    @Override
    public RatingPoint findById(UUID id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such rating"));
    }
}
