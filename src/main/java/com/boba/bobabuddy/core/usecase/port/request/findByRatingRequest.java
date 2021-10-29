package com.boba.bobabuddy.core.usecase.port.request;

/***
 * Class that stores information required to perform a query by field rating.
 */
public class findByRatingRequest {
    private float rating;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
