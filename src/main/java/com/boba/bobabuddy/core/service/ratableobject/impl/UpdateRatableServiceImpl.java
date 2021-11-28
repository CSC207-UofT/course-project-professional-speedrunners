package com.boba.bobabuddy.core.service.ratableobject.impl;

import com.boba.bobabuddy.core.data.dao.RatableObjectJpaRepository;
import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.ratableobject.UpdateRatableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/***
 * Usecase for updating ratable objects in the database
 * Only handles requests related to rating objects.
 */
@Service("UpdateRatableService")
@Transactional
public class UpdateRatableServiceImpl implements UpdateRatableService {
    private final RatableObjectJpaRepository<RatableObject> repo;

    /***
     * Constructor to inject DAO dependency
     * @param repo the DAO for RatableObject
     */
    @Autowired
    public UpdateRatableServiceImpl(RatableObjectJpaRepository<RatableObject> repo) {
        this.repo = repo;
    }


    @Override
    public RatableObject addRating(RatableObject ratable, Rating newRating) throws DuplicateResourceException {
        if (ratable.addRating(newRating)) return repo.save(ratable);
        throw new DuplicateResourceException("Rating already exist");
    }

    @Override
    public RatableObject removeRating(RatableObject ratable, Rating rating) throws ResourceNotFoundException {
        if (ratable.removeRating(rating)) return repo.save(ratable);
        throw new ResourceNotFoundException("Specified ratable does not contain this rating");
    }


    @Override
    public RatableObject updateRating(RatableObject ratable, Rating rating, int oldRating, int newRating)
            throws ResourceNotFoundException {
        if (ratable.updateRating(rating, oldRating, newRating)) return repo.save(ratable);
        throw new ResourceNotFoundException("Specified ratable does not contain this rating");
    }


}
