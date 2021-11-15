package com.boba.bobabuddy.core.usecase.ratableobject;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.ratableobject.port.IUpdateRatable;
import com.boba.bobabuddy.infrastructure.dao.RatableObjectJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/***
 * Usecase for updating ratable objects in the database
 * Only handles requests related to rating objects.
 */
@Service
@Transactional
public class UpdateRatable implements IUpdateRatable {
    private final RatableObjectJpaRepository<RatableObject> repo;

    /***
     * Constructor to inject DAO dependency
     * @param repo the DAO for RatableObject
     */
    @Autowired
    public UpdateRatable(RatableObjectJpaRepository<RatableObject> repo) {
        this.repo = repo;
    }


    /***
     * Adding a rating to a ratable object
     * @param ratable the ratable object to append rating to
     * @param newRating the rating to be appended
     * @return the mutated ratable object
     * @throws DuplicateResourceException thrown when the rating already exist within the object
     */
    @Override
    public RatableObject addRating(RatableObject ratable, Rating newRating) throws DuplicateResourceException {
        if (ratable.addRating(newRating)) return repo.save(ratable);
        throw new DuplicateResourceException("Rating already exist");
    }

    /***
     * removing a rating from a ratable object
     * @param ratable the ratable object to be mutated
     * @param rating the rating to be removed
     * @return the mutated ratable object
     * @throws ResourceNotFoundException thrown when the object does not contain the rating
     */
    @Override
    public RatableObject removeRating(RatableObject ratable, Rating rating) throws ResourceNotFoundException {
        if (ratable.removeRating(rating)) return repo.save(ratable);
        throw new ResourceNotFoundException("Specified ratable does not contain this rating", new Exception());
    }

    /**
     * update the avgRating of a ratable object
     *
     * @param ratable   the ratable object to be mutated
     * @param rating    the updated rating
     * @param oldRating the old rating value
     * @param newRating the new rating value
     * @return the mutated ratable object
     * @throws ResourceNotFoundException thrown when the object does not contain the rating
     */
    @Override
    public RatableObject updateRating(RatableObject ratable, Rating rating, int oldRating, int newRating)
            throws ResourceNotFoundException {
        if (ratable.updateRating(rating, oldRating, newRating)) return repo.save(ratable);
        throw new ResourceNotFoundException("Specified ratable does not contain this rating", new Exception());
    }


}
