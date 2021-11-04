package com.boba.bobabuddy.infrastructure.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class StoreRepresentation extends RepresentationModel<StoreRepresentation> {
    private final UUID id;
    private final String name;
    private final Set<RatingPointRepresentation> ratings;
    private final float avgRating;
    private final String location;
    private final List<ItemRepresentation> menu;

    public StoreRepresentation(UUID id, String name, Set<RatingPointRepresentation> ratings, float avgRating, String location, List<ItemRepresentation> menu) {
        this.id = id;
        this.name = name;
        this.ratings = ratings;
        this.avgRating = avgRating;
        this.location = location;
        this.menu = menu;
    }
}
