package com.boba.bobabuddy.infrastructure.dto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class StoreRepresentationBuilder {
    private UUID id;
    private String name;
    private Set<RatingPointRepresentation> ratings;
    private float avgRating;
    private String location;
    private List<ItemRepresentation> menu;

    public StoreRepresentationBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public StoreRepresentationBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StoreRepresentationBuilder setRatings(Set<RatingPointRepresentation> ratings) {
        this.ratings = ratings;
        return this;
    }

    public StoreRepresentationBuilder setAvgRating(float avgRating) {
        this.avgRating = avgRating;
        return this;
    }

    public StoreRepresentationBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public StoreRepresentationBuilder setMenu(List<ItemRepresentation> menu) {
        this.menu = menu;
        return this;
    }

    public StoreRepresentation createStoreRepresentation() {
        return new StoreRepresentation(id, name, ratings, avgRating, location, menu);
    }
}