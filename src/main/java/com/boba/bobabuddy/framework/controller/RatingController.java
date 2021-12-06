package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.data.dto.RatingDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.service.rating.CreateRatingService;
import com.boba.bobabuddy.core.service.rating.FindRatingService;
import com.boba.bobabuddy.core.service.rating.RemoveRatingService;
import com.boba.bobabuddy.core.service.rating.UpdateRatingService;
import com.boba.bobabuddy.framework.converter.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * Controller for RatingPoint related api calls.
 */
@RestController
@RequiredArgsConstructor
public class RatingController {

    // Input Boundary
    private final CreateRatingService createRating;
    private final FindRatingService findRating;
    private final RemoveRatingService removeRating;
    private final UpdateRatingService updateRating;
    private final DtoConverter<Rating, RatingDto> converter;


    /**
     * Handles POST requests to add a Rating to the database.
     *
     * @param createRatingRequest DTO class containing the data to construct a new RatingPoint entity
     * @param id                  the id of the rated RatableObject
     * @param email               the email of the user who made the rating
     * @return the constructed RatingPoint
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{ratableType}/{id}/ratings", params = "createdBy")
    @PreAuthorize("isAuthenticated()")
    public RatingDto createRating(@RequestBody RatingDto createRatingRequest, @PathVariable String ratableType,
                                  @PathVariable UUID id, @RequestParam("createdBy") String email) throws MalformedURLException {

        if(ratableType.equals("items")) ratableType = "Item";
        else if(ratableType.equals("stores")) ratableType = "Store";
        else throw new MalformedURLException("/items/ or /stores/ expected.");

        Rating rating = createRating.create(createRatingRequest, ratableType, id, email);
        return converter.convertToDto(rating);
    }

    /**
     * Handles GET requests for all Rating entities in the database.
     *
     * @return the list of all Rating entities
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/ratings")
    public List<RatingDto> findAll() {
        return converter.convertToDtoList(findRating.findAll());
    }

    /**
     * Handles GET requests for all Rating entities belonging to a RatableObject.
     *
     * @param ratableType the name of a RatableObject subclass
     * @param id            the id of the RatableObject of the same subclass
     * @return the list of Rating entities belonging to the RatableObject
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{ratableType}/{id}/ratings")
    public List<RatingDto> findByRatableObject(@PathVariable String ratableType, @PathVariable UUID id) throws MalformedURLException {
        if ("items".equals(ratableType)) {
            return converter.convertToDtoList(findRating.findByItem(id));
        }
        if ("stores".equals(ratableType)) {
            return converter.convertToDtoList(findRating.findByStore(id));
        }
        throw new MalformedURLException("must be /items/ or /stores/");
    }

    /**
     * Handles GET requests for all Rating entities belonging to a User.
     *
     * @param email the email of the User
     * @return the list of Rating entities belonging to the User
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/users/{email}/ratings")
    public List<RatingDto> findByUser(@PathVariable String email) {
        return converter.convertToDtoList(findRating.findByUser(email));
    }

    /**
     * Handles GET requests for a Rating by its UUID.
     *
     * @param id the UUID of the Rating
     * @return the Rating with the matching UUID
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/ratings/{id}")
    public RatingDto findById(@PathVariable UUID id) {
        return converter.convertToDto(findRating.findById(id));
    }

    /**
     * Handles DELETE requests to remove Rating by its UUID.
     * Respond with 204 NO_CONTENT if successful
     *
     * @param id the UUID if the Rating to be removed from the database
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/ratings/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') || principal.username == @FindUserService.findByRating(#id).getEmail()")
    public void removeById(@PathVariable UUID id) {
        removeRating.removeById(id);
    }

    /**
     * Handles PUT requests to update an existing Rating.
     *
     * @param id     the UUID of the Rating to be updated
     * @param rating the new value of the Rating
     * @return the updated Rating
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/ratings/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') || principal.username == @FindUserService.findByRating(#id).getEmail()")
    public RatingDto updateRating(@PathVariable UUID id, @RequestBody RatingDto rating) {
        return converter.convertToDto(updateRating.updateRating(id, rating.getRating()));
    }
}
