package com.boba.bobabuddy.core.usecase.rating;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.ratableobject.port.IFindRatable;
import com.boba.bobabuddy.core.usecase.ratableobject.port.IUpdateRatable;
import com.boba.bobabuddy.core.usecase.rating.port.ICreateRating;
import com.boba.bobabuddy.core.usecase.user.port.IFindUser;
import com.boba.bobabuddy.core.usecase.user.port.IUpdateUser;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This is the usecase responsible for creating new Rating entities and adding them to the system.
 */
@Service
@Transactional
public class CreateRating implements ICreateRating {

    private final RatingJpaRepository repo;
    private final IFindRatable findRatable;
    private final IUpdateRatable updateRatable;
    private final IFindUser findUser;
    private final IUpdateUser updateUser;

    /***
     * Constructor for injecting relevant use cases and DAO
     * @param repo DAO for rating entity
     * @param findRatable FindRatable usecase to find associated ratable entity
     * @param updateRatable UpdateRatable usecase to append association to ratable entity
     * @param findUser FindUser usecase to find associated user entity
     * @param updateUser UpdateUser usecase to append association to user entity
     */
    @Autowired
    public CreateRating(RatingJpaRepository repo, IFindRatable findRatable, IUpdateRatable updateRatable, IFindUser findUser, IUpdateUser updateUser) {
        this.repo = repo;
        this.findRatable = findRatable;
        this.updateRatable = updateRatable;
        this.findUser = findUser;
        this.updateUser = updateUser;
    }

    /***
     * Persist a new rating to the database and construct relevant bidirectional associations
     * @param rating new rating to be persisted
     * @param ratableId id of the associated ratable object
     * @param email email of the associated user
     * @return the persisted rating entity
     * @throws ResourceNotFoundException Thrown when either the ratable object or the user was not found
     * @throws DuplicateResourceException Thrown when this rating already exist in either the user or the ratable object
     */
    @Override
    public Rating create(Rating rating, UUID ratableId, String email)
            throws ResourceNotFoundException, DuplicateResourceException {

        RatableObject ratableToUpdate = findRatable.findById(ratableId);
        User userToUpdate = findUser.findByEmail(email);

        rating = repo.save(rating);
        rating.setRatableObject(ratableToUpdate);
        rating.setUser(userToUpdate);

        updateUser.addRating(userToUpdate, rating);
        updateRatable.addRating(ratableToUpdate, rating);

        return repo.save(rating);
    }
}
