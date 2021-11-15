package com.boba.bobabuddy.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.UUID;

/***
 * Class that represents a singleton rating
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = RatingDto.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingDto extends SimpleRatingDto {

    @JsonIdentityReference
    private UserDto userDto;

    @JsonIdentityReference
    private RatableObjectDto ratableObject;

    /***
     * Constructor for a Rating point object
     * @param rating the rating, either 0 or 1
     * @param user the user that made this rating
     * @param ratableObject the ratableObject that is being rated, either Item or Store
     */
    public RatingDto(int rating, UserDto user, RatableObjectDto ratableObject) {
        this.rating = rating;
        this.userDto = user;
        this.ratableObject = ratableObject;
    }

    public RatingDto() {
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public RatableObjectDto getRatableObject() {
        return ratableObject;
    }

    public void setRatableObject(RatableObjectDto ratableObject) {
        this.ratableObject = ratableObject;
    }
}
