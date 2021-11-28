package com.boba.bobabuddy.core.service.rating;

import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface FindRatingService {

    /**
     * Find every Rating associated with an Item by its UUID.
     *
     * @param id the UUID of the Item
     * @return a list of every RatingPoint associated with the Item
     */
    Set<Rating> findByItem(UUID id) throws ResourceNotFoundException;

    /**
     * Find every Rating associated with a Store by its UUID.
     *
     * @param id the UUID of the Store
     * @return a list of every RatingPoint associated with the Store
     */
    Set<Rating> findByStore(UUID id) throws ResourceNotFoundException;

    /**
     * Find every Rating associated with a User by its email.
     *
     * @param email the UUID of the User
     * @return a list of every Rating associated with the User
     */
    Set<Rating> findByUser(String email);

    /**
     * Find the Rating entity with the given UUID.
     *
     * @param id the UUID of the Rating
     * @return the Rating with the UUID
     * @throws ResourceNotFoundException if no Rating with the given UUID is found
     */
    Rating findById(UUID id) throws ResourceNotFoundException;

    /**
     * Find All Rating Entity within the database
     *
     * @return list of all ratings
     */
    List<Rating> findAll();
}
