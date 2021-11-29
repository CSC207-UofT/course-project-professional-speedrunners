package com.boba.bobabuddy.core.service.rating.impl;

import com.boba.bobabuddy.core.data.dao.RatingJpaRepository;
import com.boba.bobabuddy.core.data.dto.RatingDto;
import com.boba.bobabuddy.core.domain.*;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.ratableobject.FindRatableService;
import com.boba.bobabuddy.core.service.ratableobject.UpdateRatableService;
import com.boba.bobabuddy.core.service.rating.CreateRatingService;
import com.boba.bobabuddy.core.service.user.FindUserService;
import com.boba.bobabuddy.core.service.user.UpdateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This is the usecase responsible for creating new Rating entities and adding them to the system.
 */
@Service("CreateRatingService")
@Transactional
public class CreateRatingServiceImpl implements CreateRatingService {

    private final RatingJpaRepository repo;
    private final FindRatableService findRatable;
    private final UpdateRatableService updateRatable;
    private final FindUserService findUser;
    private final UpdateUserService updateUser;

    /***
     * Constructor for injecting relevant use cases and DAO
     * @param repo DAO for rating entity
     * @param findRatable FindRatable usecase to find associated ratable entity
     * @param updateRatable UpdateRatable usecase to append association to ratable entity
     * @param findUser FindUser usecase to find associated user entity
     * @param updateUser UpdateUser usecase to append association to user entity
     */
    @Autowired
    public CreateRatingServiceImpl(RatingJpaRepository repo, FindRatableService findRatable, UpdateRatableService updateRatable, FindUserService findUser, UpdateUserService updateUser) {
        this.repo = repo;
        this.findRatable = findRatable;
        this.updateRatable = updateRatable;
        this.findUser = findUser;
        this.updateUser = updateUser;
    }


    @Override
    public Rating create(RatingDto rating, String ratableType, UUID ratableId, String email)
            throws ResourceNotFoundException, DuplicateResourceException, IllegalArgumentException {
        if (rating.getRating() != 0 && rating.getRating() != 1) {
            throw new IllegalArgumentException("Rating must be 0 or 1");
        }




        RatableObject ratableToUpdate = findRatable.findByTypeAndId(ratableType, ratableId);
        User userToUpdate = findUser.findByEmail(email);

        Rating createdRating = Rating.builder()
                .rating(rating.getRating())
                .build();

        updateUser.addRating(userToUpdate, createdRating);
        updateRatable.addRating(ratableToUpdate, createdRating);

        return repo.save(createdRating);
    }
}
