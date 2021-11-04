package com.boba.bobabuddy.infrastructure.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Set;
import java.util.UUID;

public class ItemRepresentation extends RatableObjectRepresentation<ItemRepresentation> {
    private final float price;
    private final StoreRepresentation store;

    public ItemRepresentation(UUID id, String name, Set<RatingPointRepresentation> ratings, float avgRating, float price, StoreRepresentation store) {
        super(id, name, ratings, avgRating);
        this.price = price;
        this.store = store;
    }
}
