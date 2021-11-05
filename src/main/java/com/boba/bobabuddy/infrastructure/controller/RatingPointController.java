package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.ICreateRatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IFindRatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IRemoveRatingPoint;
import com.boba.bobabuddy.core.usecase.port.ratingpointport.IUpdateRatingPoint;
import com.boba.bobabuddy.core.usecase.port.request.CreateRatingPointRequest;
import com.boba.bobabuddy.infrastructure.assembler.RatingPointResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
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
    private final RatingPointResourceAssembler assembler;

    @Autowired
    public RatingPointController(ICreateRatingPoint createRatingPoint, IFindRatingPoint findRatingPoint,
                                 IRemoveRatingPoint removeRatingPoint, IUpdateRatingPoint updateRatingPoint, RatingPointResourceAssembler assembler) {
        this.createRatingPoint = createRatingPoint;
        this.findRatingPoint = findRatingPoint;
        this.removeRatingPoint = removeRatingPoint;
        this.updateRatingPoint = updateRatingPoint;
        this.assembler = assembler;
        this.assembler.setBasePath("/api");
    }

    /**
     * Add a RatingPoint to the database.
     * TODO: Move illegal argument exception generation to usecase instead of DTO
     * @param createRatingPointRequest request class containing the data to construct a new RatingPoint entity
     * @return the constructed RatingPoint
     */

    @PostMapping(path = "/api/{ratableObject}/{id}/ratings", params = "createdBy")
    public ResponseEntity<EntityModel<RatingPoint>> createRatingPoint(@RequestBody CreateRatingPointRequest createRatingPointRequest,
                                                                      @PathVariable UUID id, @RequestParam("createdBy") String email){
        try {
            return ResponseEntity.ok(assembler.toModel(createRatingPoint.create(createRatingPointRequest.getRatingPoint(), id, email)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DuplicateResourceException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    /**
     * Find every RatingPoint associated with a RatableObject.
     * TODO: Should not search on store when ratableObject parameter is item, and vice versa
     *
     * @return the list RatingPoint entities associated the RatableObject
     */
    @GetMapping(path = "/api/{ratableObject}/{id}/ratings")
    public ResponseEntity<CollectionModel<EntityModel<RatingPoint>>> findByRatableObject(@PathVariable String ratableObject, @PathVariable UUID id) {
        try {
            if (ratableObject.equals("items") || ratableObject.equals("stores")) {
                return ResponseEntity.ok(assembler.toCollectionModel(findRatingPoint.findByRatableObject(id)));
            }
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        Exception e = new MalformedURLException("must be /api/item/ or /api/store/");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }

    /**
     * Find every RatingPoint associated with a User.
     *
     * @return the list RatingPoint entities associated the User
     */
    @GetMapping(path = "/api/users/{email}/ratings")
    public ResponseEntity<CollectionModel<EntityModel<RatingPoint>>> findByUser(@PathVariable String email) {
        return ResponseEntity.ok(assembler.toCollectionModel(findRatingPoint.findByUser(email)));
    }

    /**
     * Find a RatingPoint entity by UUID.
     *
     * @param id request class containing the UUID of the RatingPoint
     * @return the RatingPoint with the UUID
     */
    @GetMapping(path = "/api/ratings/{id}")
    public ResponseEntity<EntityModel<RatingPoint>> findById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(assembler.toModel(findRatingPoint.findById(id)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Remove a RatingPoint entity by UUID.
     *
     * @param id request class containing the UUID of the RatingPoint
     * @return the RatingPoint removed
     */
    @DeleteMapping(path = "/api/ratings/{id}")
    public ResponseEntity<EntityModel<RatingPoint>> removeById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(assembler.toModel(removeRatingPoint.removeById(id)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Update the rating of a RatingPoint.
     *
     * @param id request class containing the UUID of the RatingPoint and the new rating
     * @return the updated RatingPoint
     */

    @PutMapping(path = "/api/ratings/{id}", params = "rate")
    public ResponseEntity<EntityModel<RatingPoint>> updateRatingPointRating(@PathVariable UUID id, @RequestParam int rate) {
        try {
            return ResponseEntity.ok(assembler.toModel(updateRatingPoint.updateRatingPointRating(id,
                    rate)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

    }
}
