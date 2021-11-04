package com.boba.bobabuddy.core.usecase.ratableobject;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.ratableport.IUpdateRating;
import com.boba.bobabuddy.infrastructure.database.RatableObjectJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateRatable implements IUpdateRating {
    private final RatableObjectJpaRepository<RatableObject> repo;

    @Autowired
    public UpdateRatable(RatableObjectJpaRepository<RatableObject> repo) {
        this.repo = repo;
    }


    @Override
    public RatableObject addRating(RatableObject ratable, RatingPoint newRating) {
        ratable.addRating(newRating);
        newRating.setRatableObject(ratable);
        return repo.save(ratable);
    }

    @Override
    public RatableObject removeRating(RatableObject ratable, RatingPoint newRating) throws ResourceNotFoundException {
        if (ratable.removeRating(newRating)) {
            return repo.save(ratable);
        }
        throw new ResourceNotFoundException("Rating does not belong", new Exception());
    }

}
