package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.rating.port.ICreateRating;
import com.boba.bobabuddy.core.usecase.rating.port.IFindRating;
import com.boba.bobabuddy.core.usecase.rating.port.IRemoveRating;
import com.boba.bobabuddy.core.usecase.rating.port.IUpdateRating;
import com.boba.bobabuddy.infrastructure.assembler.RatingResourceAssembler;
import com.boba.bobabuddy.infrastructure.dto.RatingDto;
import com.boba.bobabuddy.infrastructure.dto.SimpleRatingDto;
import com.boba.bobabuddy.infrastructure.dto.converter.FullDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller for RatingPoint related api calls.
 */
@RestController
@Component("RatingController")
public class RatingController {

    // Input Boundary
    private final ICreateRating createRating;
    private final IFindRating findRating;
    private final IRemoveRating removeRatingPoint;
    private final IUpdateRating updateRatingPoint;
    private final RatingResourceAssembler assembler;
    private final FullDtoConverter<Rating, SimpleRatingDto, RatingDto> fullDtoConverter;


    @Autowired
    public RatingController(ICreateRating createRating, IFindRating findRating,
                            IRemoveRating removeRatingPoint, IUpdateRating updateRatingPoint,
                            RatingResourceAssembler assembler, ModelMapper mapper) {
        this.createRating = createRating;
        this.findRating = findRating;
        this.removeRatingPoint = removeRatingPoint;
        this.updateRatingPoint = updateRatingPoint;
        this.assembler = assembler;

        this.fullDtoConverter = new FullDtoConverter<>(mapper, Rating.class, SimpleRatingDto.class, RatingDto.class);
    }

    /**
     * Handles POST requests to add a Rating to the database.
     *
     * @param createRatingRequest      DTO class containing the data to construct a new RatingPoint entity
     * @param id                       the id of the rated RatableObject
     * @param email                    the email of the user who made the rating
     * @return the constructed RatingPoint
     */
    @PostMapping(path = "/user/{ratableObject}/{id}/ratings", params = "createdBy")
    @PreAuthorize("#email == authentication.principal.username || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EntityModel<RatingDto>> createRating(@RequestBody SimpleRatingDto createRatingRequest,
                                                               @PathVariable UUID id, @RequestParam("createdBy") String email) {
        Rating rating = createRating.create(fullDtoConverter.convertToEntityFromSimple(createRatingRequest), id, email);
        RatingDto ratingToPresent = fullDtoConverter.convertToDto(rating);
        return ResponseEntity.created(linkTo(methodOn(RatingController.class).findById(ratingToPresent.getId())).toUri()).body(assembler.toModel(ratingToPresent));
    }

    /**
     * Handles GET requests for all Rating entities in the database.
     *
     * @return the list of all Rating entities
     */
    @GetMapping(path = "/ratings")
    public ResponseEntity<CollectionModel<EntityModel<RatingDto>>> findAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(fullDtoConverter.convertToDtoCollection(findRating.findAll())));
    }

    /**
     * Handles GET requests for all Rating entities belonging to a RatableObject.
     *
     * @param ratableObject the name of a RatableObject subclass
     * @param id            the id of the RatableObject of the same subclass
     * @return the list of Rating entities belonging to the RatableObject
     */
    @GetMapping(path = "/{ratableObject}/{id}/ratings")
    public ResponseEntity<CollectionModel<EntityModel<RatingDto>>> findByRatableObject(@PathVariable String ratableObject, @PathVariable UUID id) {
        if (ratableObject.equals("items")) {
            return ResponseEntity.ok(assembler.toCollectionModel(fullDtoConverter.convertToDtoCollection(findRating.findByItem(id))));
        }
        if (ratableObject.equals("stores")) {
            return ResponseEntity.ok(assembler.toCollectionModel(fullDtoConverter.convertToDtoCollection(findRating.findByStore(id))));
        }
        Exception e = new MalformedURLException("must be /item/ or /store/");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }

    /**
     * Handles GET requests for all Rating entities belonging to a User.
     *
     * @param email the email of the User
     * @return the list of Rating entities belonging to the User
     */
    @GetMapping(path = "/users/{email}/ratings")
    public ResponseEntity<CollectionModel<EntityModel<RatingDto>>> findByUser(@PathVariable String email) {
        return ResponseEntity.ok(assembler.toCollectionModel(fullDtoConverter.convertToDtoCollection(findRating.findByUser(email))));
    }

    /**
     * Handles GET requests for a Rating by its UUID.
     *
     * @param id the UUID of the Rating
     * @return the Rating with the matching UUID
     */
    @GetMapping(path = "/ratings/{id}")
    public ResponseEntity<EntityModel<RatingDto>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toModel(fullDtoConverter.convertToDto(findRating.findById(id))));
    }

    /**
     * Handles DELETE requests to remove Rating by its UUID.
     *
     * @param id the UUID if the Rating to be removed from the database
     * @return NO_CONTENT http status
     */
    @DeleteMapping(path = "/ratings/{id}")
    @PreAuthorize("@RatingController.getFindRating().findById(id).getUser().getEmail() == authentication.principal.username || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> removeById(@PathVariable UUID id) {
        removeRatingPoint.removeById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Handles PUT requests to update an existing Rating.
     *
     * @param id     the UUID of the Rating to be updated
     * @param rating the new value of the Rating
     * @return the updated Rating
     */
    @PutMapping(path = "/ratings/{id}")
    @PreAuthorize("@RatingController.getFindRating().findById(id).getUser().getEmail() == authentication.principal.username || hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EntityModel<RatingDto>> updateRating(@PathVariable UUID id, @RequestBody SimpleRatingDto rating) {
        return ResponseEntity.ok(assembler.toModel(fullDtoConverter.convertToDto(updateRatingPoint.updateRating(id, rating.getRating()))));
    }

    // TODO: consider moving get methods to a common component?
    public IFindRating getFindRating() {
        return findRating;
    }
}
