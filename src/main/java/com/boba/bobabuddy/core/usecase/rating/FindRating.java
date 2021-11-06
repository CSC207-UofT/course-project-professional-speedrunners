package com.boba.bobabuddy.core.usecase.rating;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.ratableobject.port.IFindRatable;
import com.boba.bobabuddy.core.usecase.rating.port.IFindRating;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This is the usecase responsible for finding existing Rating entities in the system.
 */
@Service
@Transactional
public class FindRating implements IFindRating {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;
    private final IFindRatable findRatable;

    /**
     * Constructor for the FindRating usecase.
     *
     * @param repo the RatingJpaRepository to be searched for Rating entities
     */
    @Autowired
    public FindRating(RatingJpaRepository repo, IFindRatable findRatable) {
        this.repo = repo;
        this.findRatable = findRatable;
    }

    /**
     * Find every Rating associated with a RatableObject by its UUID.
     * TODO: consider moving this usecase to RatableObject since it stores its ratings
     *
     * @param id the UUID of the RatableObject
     * @return a list of every RatingPoi.nt associated with the RatableObject
     */
    @Override
    public Set<Rating> findByRatableObject(UUID id) throws ResourceNotFoundException {
        return findRatable.findById(id).getRatings();
    }

    /**
     * Find every Rating associated with a User by its email.
     * TODO: consider moving this usecase to User since it stores its ratings
     *
     * @param email the UUID of the User
     * @return a list of every Rating associated with the User
     */
    @Override
    public List<Rating> findByUser(String email) {
        return repo.findByUser_email(email);
    }

    /**
     * Find the Rating entity with the given UUID.
     *
     * @param id the UUID of the Rating
     * @return the Rating with the UUID
     * @throws ResourceNotFoundException if no Rating with the given UUID is found
     */
    @Override
    public Rating findById(UUID id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such rating"));
    }

    @Override
    public List<Rating> findAll() {
        return repo.findAll();
    }
}
