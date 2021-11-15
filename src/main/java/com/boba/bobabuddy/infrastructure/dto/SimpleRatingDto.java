package com.boba.bobabuddy.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = SimpleRatingDto.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "ratings", itemRelation = "rating")
public class SimpleRatingDto {
    protected int rating;
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
