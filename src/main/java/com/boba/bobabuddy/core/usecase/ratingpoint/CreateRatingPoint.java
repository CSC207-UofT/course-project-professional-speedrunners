package com.boba.bobabuddy.core.usecase.ratingpoint;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.ICreateRatingPoint;
import com.boba.bobabuddy.core.usecase.ratableobject.FindRatable;
import com.boba.bobabuddy.core.usecase.ratableobject.UpdateRatable;
import com.boba.bobabuddy.core.usecase.user.FindUser;
import com.boba.bobabuddy.core.usecase.user.UpdateUser;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This is the usecase responsible for creating new RatingPoint entities and adding them to the system.
 */
@Service
@Transactional
public class CreateRatingPoint implements ICreateRatingPoint {
    /**
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final RatingJpaRepository repo;
    private final FindRatable findRatable;
    private final UpdateRatable updateRatable;
    private final FindUser findUser;
    private final UpdateUser updateUser;

    /**
     * Constructor for the CreateRatingPoint usecase.
     *
     * @param repo          a RatingJpaRepository for storing the new RatingPoint objects
     * @param findRatable
     * @param updateRatable
     * @param findUser
     * @param updateUser
     */
    @Autowired
    public CreateRatingPoint(RatingJpaRepository repo, FindRatable findRatable, UpdateRatable updateRatable, FindUser findUser, UpdateUser updateUser) {
        this.repo = repo;
        this.findRatable = findRatable;
        this.updateRatable = updateRatable;
        this.findUser = findUser;
        this.updateUser = updateUser;
    }

    /**
     * Save a RatingPoint to the database.
     * TODO: add the new RatingPoint to the associated User and RatableObject
     *
     * @param ratingPoint the RatingPoint to be saved in the database
     * @return the saved RatingPoint
     */
    @Override
    public RatingPoint create(RatingPoint ratingPoint, UUID ratableId, String email) throws Exception {
        RatableObject ratableToUpdate = findRatable.findById(ratableId);
        User userToUpdate = findUser.findByEmail(email);

        ratingPoint = repo.save(ratingPoint);
        ratingPoint.setRatableObject(ratableToUpdate);
        ratingPoint.setUser(userToUpdate);

        updateUser.addRating(userToUpdate, ratingPoint);
        updateRatable.addRating(ratableToUpdate, ratingPoint);



        return repo.save(ratingPoint);
    }
}
