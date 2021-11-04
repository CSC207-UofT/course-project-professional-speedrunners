package com.boba.bobabuddy.infrastructure.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Set;
import java.util.UUID;

public class RatableObjectRepresentation<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {
    protected final UUID id;
    protected final String name;
    protected final Set<RatingPointRepresentation> ratings;
    protected final float avgRating;

    public RatableObjectRepresentation(UUID id, String name, Set<RatingPointRepresentation> ratings, float avgRating) {
        this.id = id;
        this.name = name;
        this.ratings = ratings;
        this.avgRating = avgRating;
    }
}
