package com.boba.bobabuddy.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

/**
 * Simple Data Transfer Objects with no nested fields
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = SimpleItemDto.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "items", itemRelation = "item")
public class SimpleItemDto implements BaseRatableObjectDto {
    private String name;
    private float price;
    private UUID id;
    private float avgRating;

    public SimpleItemDto(String name, float price, float avgRating) {
        this.name = name;
        this.price = price;
        this.avgRating = avgRating;
    }

    public SimpleItemDto() {
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
