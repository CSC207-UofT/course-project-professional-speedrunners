package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.ICreateRatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IFindRatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IRemoveRatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IUpdateRatingPoint;
import com.boba.bobabuddy.core.usecase.port.request.*;
import com.boba.bobabuddy.core.usecase.ratingpoint.exceptions.InvalidRatingException;
import com.boba.bobabuddy.core.usecase.ratingpoint.exceptions.RatingPointNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for RatingPoint related api calls.
 */
@RestController
public class RatingPointController {

    // Input Boundary
    private final ICreateRatingPoint createRatingPoint;
    private final IFindRatingPoint findRatingPoint;
    private final IRemoveRatingPoint removeRatingPoint;
    private final IUpdateRatingPoint updateRatingPoint;

    @Autowired
    public RatingPointController(ICreateRatingPoint createRatingPoint, IFindRatingPoint findRatingPoint,
                                 IRemoveRatingPoint removeRatingPoint, IUpdateRatingPoint updateRatingPoint) {
        this.createRatingPoint = createRatingPoint;
        this.findRatingPoint = findRatingPoint;
        this.removeRatingPoint = removeRatingPoint;
        this.updateRatingPoint = updateRatingPoint;
    }

    /**
     * Add a RatingPoint to the database.
     *
     * @param createRatingPointRequest request class containing the data to construct a new RatingPoint entity
     * @return the constructed RatingPoint
     */

    @PostMapping(path = "/api/{ratableObject}/{id}/rating/", params = "createdBy")
    public RatingPoint createRatingPoint(@RequestBody CreateRatingPointRequest createRatingPointRequest,
                                         @PathVariable UUID id, @RequestParam("createdBy") String email) throws Exception {
        return createRatingPoint.create(createRatingPointRequest.getRatingPoint(), id, email);
    }

    /**
     * Find every RatingPoint associated with a RatableObject.
     *
     * @param findByIdRequest request class containing the UUID of the RatableObject
     * @return the list RatingPoint entities associated the RatableObject
     */
    public List<RatingPoint> findByRatableObject(FindByIdRequest findByIdRequest) {
        return findRatingPoint.findByRatableObject(findByIdRequest.getId());
    }

    /**
     * Find every RatingPoint associated with a User.
     *
     * @param findByEmailRequest request class containing the email of the User
     * @return the list RatingPoint entities associated the User
     */
    public List<RatingPoint> findByUser(FindByEmailRequest findByEmailRequest) {
        return findRatingPoint.findByUser(findByEmailRequest.getEmail());
    }

    /**
     * Find a RatingPoint entity by UUID.
     *
     * @param findByIdRequest request class containing the UUID of the RatingPoint
     * @return the RatingPoint with the UUID
     * @throws RatingPointNotFoundException if no RatingPoint with the given UUID is found
     */
    public RatingPoint findById(FindByIdRequest findByIdRequest) throws RatingPointNotFoundException {
        return findRatingPoint.findById(findByIdRequest.getId());
    }

    /**
     * Remove a RatingPoint entity by UUID.
     *
     * @param removeByIdRequest request class containing the UUID of the RatingPoint
     * @return the RatingPoint removed
     */
    public RatingPoint removeById(RemoveByIdRequest removeByIdRequest) throws ResourceNotFoundException {
        return removeRatingPoint.removeById(removeByIdRequest.getId());
    }

    /**
     * Update the rating of a RatingPoint.
     *
     * @param updateRatingPointRequest request class containing the UUID of the RatingPoint and the new rating
     * @return the updated RatingPoint
     * @throws RatingPointNotFoundException if no RatingPoint with the given UUID is found
     * @throws InvalidRatingException       if the new rating is not 1 or 0
     */
    public RatingPoint updateRatingPointRating(UpdateRatingPointRequest updateRatingPointRequest)
            throws RatingPointNotFoundException, InvalidRatingException {
        return updateRatingPoint.updateRatingPointRating(updateRatingPointRequest.getId(),
                updateRatingPointRequest.getRating());
    }
}
