package com.boba.bobabuddy.core.usecase.port.request;

import java.util.UUID;

public class UpdateRatingPointRequest {
    private UUID id;
    private int rating;

    public UUID getId() {
        return id;
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
