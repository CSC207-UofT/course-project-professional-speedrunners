package com.boba.bobabuddy.core.usecase.request;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.User;

/**
 * Class that stores the information required to create a new RatingPoint entity.
 */

public class CreateRatingPointRequest {
    private int rating;
    private User user;
    private RatableObject ratableObject;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RatableObject getRatableObject() {
        return ratableObject;
    }

    public void setRatableObject(RatableObject ratableObject) {
        this.ratableObject = ratableObject;
    }

    public Rating getRatingPoint() {
        return new Rating(rating, user, ratableObject);
    }
}
