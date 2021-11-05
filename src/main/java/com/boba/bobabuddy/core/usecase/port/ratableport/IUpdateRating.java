package com.boba.bobabuddy.core.usecase.port.ratableport;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

public interface IUpdateRating {
    public RatableObject addRating(RatableObject ratable, RatingPoint newRating);

    public RatableObject removeRating(RatableObject ratable, RatingPoint newRating) throws ResourceNotFoundException;


}
