package com.boba.bobabuddy.core.entity.builder;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.entity.User;

public class RatingPointBuilder {
    private int rating;
    private User user;
    private RatableObject ratableObject;

    public RatingPointBuilder setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public RatingPointBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public RatingPointBuilder setRatableObject(RatableObject ratableObject) {
        this.ratableObject = ratableObject;
        return this;
    }

    public RatingPoint createRatingPoint() {
        return new RatingPoint(rating, user, ratableObject);
    }
}