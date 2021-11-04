package com.boba.bobabuddy.infrastructure.dto;

import java.util.Set;
import java.util.UUID;

public class ItemRepresentationBuilder {
    private UUID id;
    private String name;
    private Set<RatingPointRepresentation> ratings;
    private float avgRating;
    private float price;
    private StoreRepresentation store;

    public ItemRepresentationBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public ItemRepresentationBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemRepresentationBuilder setRatings(Set<RatingPointRepresentation> ratings) {
        this.ratings = ratings;
        return this;
    }

    public ItemRepresentationBuilder setAvgRating(float avgRating) {
        this.avgRating = avgRating;
        return this;
    }

    public ItemRepresentationBuilder setPrice(float price) {
        this.price = price;
        return this;
    }

    public ItemRepresentationBuilder setStore(StoreRepresentation store) {
        this.store = store;
        return this;
    }

    public ItemRepresentation createItemRepresentation() {
        return new ItemRepresentation(id, name, ratings, avgRating, price, store);
    }
}