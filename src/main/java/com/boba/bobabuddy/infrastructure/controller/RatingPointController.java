package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
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

import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
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
                                         @PathVariable UUID id, @RequestParam("createdBy") String email)
            throws DuplicateResourceException, ResourceNotFoundException {
        return createRatingPoint.create(createRatingPointRequest.getRatingPoint(), id, email);
    }

    /**
     * Find every RatingPoint associated with a RatableObject.
     *
     * @return the list RatingPoint entities associated the RatableObject
     */
    @GetMapping(path = "/api/{ratableObject}/{id}/rating/")
    public Set<RatingPoint> findByRatableObject(@PathVariable String ratableObject, @PathVariable UUID id)
            throws MalformedURLException, ResourceNotFoundException {
        if(ratableObject.equals("item") || ratableObject.equals("store")){
            return findRatingPoint.findByRatableObject(id);
        } throw new MalformedURLException("must be /api/item/ or /api/store/");
    }

    /**
     * Find every RatingPoint associated with a User.
     *
     * @return the list RatingPoint entities associated the User
     */
    @GetMapping(path = "/api/user/{email}/rating/")
    public List<RatingPoint> findByUser(@PathVariable String email) {
        return findRatingPoint.findByUser(email);
    }

    /**
     * Find a RatingPoint entity by UUID.
     *
     * @param id request class containing the UUID of the RatingPoint
     * @return the RatingPoint with the UUID
     * @throws ResourceNotFoundException if no RatingPoint with the given UUID is found
     */
    @GetMapping(path = "/api/rating/{id}")
    public RatingPoint findById(@PathVariable UUID id) throws ResourceNotFoundException {
        return findRatingPoint.findById(id);
    }

    /**
     * Remove a RatingPoint entity by UUID.
     *
     * @param id request class containing the UUID of the RatingPoint
     * @return the RatingPoint removed
     */
    @DeleteMapping(path = "/api/rating/{id}")
    public RatingPoint removeById(@PathVariable UUID id) throws ResourceNotFoundException {
        return removeRatingPoint.removeById(id);
    }

    /**
     * Update the rating of a RatingPoint.
     *
     * @param id request class containing the UUID of the RatingPoint and the new rating
     * @return the updated RatingPoint
     * @throws ResourceNotFoundException if no RatingPoint with the given UUID is found
     * @throws IllegalArgumentException       if the new rating is not 1 or 0
     */

    @PutMapping(path = "/api/rating/{id}", params = "rate")
    public RatingPoint updateRatingPointRating(@PathVariable UUID id, @RequestParam int rate)
            throws IllegalArgumentException, ResourceNotFoundException {
        return updateRatingPoint.updateRatingPointRating(id,
                rate);
    }
}
