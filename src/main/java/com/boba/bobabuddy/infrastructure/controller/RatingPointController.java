package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.port.request.CreateRatingPointRequest;
import com.boba.bobabuddy.core.usecase.port.request.FindByIdRequest;
import com.boba.bobabuddy.core.usecase.port.request.RemoveByIdRequest;
import com.boba.bobabuddy.core.usecase.port.request.UpdateRatingPointRequest;
import com.boba.bobabuddy.core.usecase.ratingpoint.CreateRatingPoint;
import com.boba.bobabuddy.core.usecase.ratingpoint.FindRatingPoint;
import com.boba.bobabuddy.core.usecase.ratingpoint.RemoveRatingPoint;
import com.boba.bobabuddy.core.usecase.ratingpoint.UpdateRatingPoint;
import com.boba.bobabuddy.core.usecase.ratingpoint.exceptions.InvalidRatingException;
import com.boba.bobabuddy.core.usecase.ratingpoint.exceptions.RatingPointNotFoundException;

import java.util.List;

public class RatingPointController {
    private CreateRatingPoint createRatingPoint;
    private FindRatingPoint findRatingPoint;
    private RemoveRatingPoint removeRatingPoint;
    private UpdateRatingPoint updateRatingPoint;

    public RatingPoint createRatingPoint(CreateRatingPointRequest createRatingPointRequest) {
        return createRatingPoint.create(createRatingPointRequest.getRatingPoint());
    }

    public List<RatingPoint> findByRatableObject(FindByIdRequest findByIdRequest) {
        return findRatingPoint.findByRatableObject(findByIdRequest.getId());
    }

    public List<RatingPoint> findByUser(FindByIdRequest findByIdRequest) {
        return findRatingPoint.findByUser(findByIdRequest.getId());
    }

    public RatingPoint findById(FindByIdRequest findByIdRequest) throws RatingPointNotFoundException {
        return findRatingPoint.findById(findByIdRequest.getId());
    }

    public RatingPoint removeById(RemoveByIdRequest removeByIdRequest) {
        return removeRatingPoint.removeById(removeByIdRequest.getId());
    }

    public RatingPoint updateRatingPointRating(UpdateRatingPointRequest updateRatingPointRequest)
            throws RatingPointNotFoundException, InvalidRatingException {
        return updateRatingPoint.updateRatingPointRating(updateRatingPointRequest.getId(),
                updateRatingPointRequest.getRating());
    }
}
