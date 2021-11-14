package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.rating.port.ICreateRating;
import com.boba.bobabuddy.core.usecase.rating.port.IFindRating;
import com.boba.bobabuddy.core.usecase.rating.port.IRemoveRating;
import com.boba.bobabuddy.core.usecase.rating.port.IUpdateRating;
import com.boba.bobabuddy.core.usecase.request.CreateRatingPointRequest;
import com.boba.bobabuddy.infrastructure.assembler.RatingResourceAssembler;
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
public class RatingController {

    // Input Boundary
    private final ICreateRating createRatingPoint;
    private final IFindRating findRatingPoint;
    private final IRemoveRating removeRatingPoint;
    private final IUpdateRating updateRatingPoint;
    private final RatingResourceAssembler assembler;

    @Autowired
    public RatingController(ICreateRating createRatingPoint, IFindRating findRatingPoint,
                            IRemoveRating removeRatingPoint, IUpdateRating updateRatingPoint, RatingResourceAssembler assembler) {
        this.createRatingPoint = createRatingPoint;
        this.findRatingPoint = findRatingPoint;
        this.removeRatingPoint = removeRatingPoint;
        this.updateRatingPoint = updateRatingPoint;
        this.assembler = assembler;
    }

    /**
     * Add a RatingPoint to the database.
     * TODO: Move illegal argument exception generation to usecase instead of DTO
     *
     * @param createRatingPointRequest request class containing the data to construct a new RatingPoint entity
     * @return the constructed RatingPoint
     */

    @PostMapping(path = "/{ratableObject}/{id}/ratings", params = "createdBy")
    public ResponseEntity<EntityModel<Rating>> createRatingPoint(@RequestBody CreateRatingPointRequest createRatingPointRequest,
                                                                 @PathVariable UUID id, @RequestParam("createdBy") String email) {
        return ResponseEntity.ok(assembler.toModel(createRatingPoint.create(createRatingPointRequest.getRatingPoint(), id, email)));
    }

    @GetMapping(path = "/ratings")
    public ResponseEntity<CollectionModel<EntityModel<Rating>>> findAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(findRatingPoint.findAll()));
    }

    /**
     * Find every RatingPoint associated with a RatableObject.
     *
     * @return the list RatingPoint entities associated the RatableObject
     */
    @GetMapping(path = "/{ratableObject}/{id}/ratings")
    public ResponseEntity<CollectionModel<EntityModel<Rating>>> findByRatableObject(@PathVariable String ratableObject, @PathVariable UUID id) {
        if (ratableObject.equals("items")) {
            return ResponseEntity.ok(assembler.toCollectionModel(findRatingPoint.findByItem(id)));
        }
        if(ratableObject.equals("stores")) {
            return ResponseEntity.ok(assembler.toCollectionModel(findRatingPoint.findByStore(id)));
        }
        Exception e = new MalformedURLException("must be /item/ or /store/");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }

    /**
     * Find every RatingPoint associated with a User.
     *
     * @return the list RatingPoint entities associated the User
     */
    @GetMapping(path = "/users/{email}/ratings")
    public ResponseEntity<CollectionModel<EntityModel<Rating>>> findByUser(@PathVariable String email) {
        return ResponseEntity.ok(assembler.toCollectionModel(findRatingPoint.findByUser(email)));
    }

    /**
     * Find a RatingPoint entity by UUID.
     *
     * @param id request class containing the UUID of the RatingPoint
     * @return the RatingPoint with the UUID
     */
    @GetMapping(path = "/ratings/{id}")
    public ResponseEntity<EntityModel<Rating>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toModel(findRatingPoint.findById(id)));

    }

    /**
     * Remove a RatingPoint entity by UUID.
     *
     * @param id request class containing the UUID of the RatingPoint
     * @return the RatingPoint removed
     */
    @DeleteMapping(path = "/ratings/{id}")
    public ResponseEntity<?> removeById(@PathVariable UUID id) {
        removeRatingPoint.removeById(id);
        return ResponseEntity.noContent().build();

    }

    /**
     * Update the rating of a RatingPoint.
     *
     * @param id request class containing the UUID of the RatingPoint and the new rating
     * @return the updated RatingPoint
     */

    @PutMapping(path = "/ratings/{id}", params = "rate")
    public ResponseEntity<EntityModel<Rating>> updateRatingPointRating(@PathVariable UUID id, @RequestParam int rate) {
        return ResponseEntity.ok(assembler.toModel(updateRatingPoint.updateRating(id,
                rate)));


    }

}
