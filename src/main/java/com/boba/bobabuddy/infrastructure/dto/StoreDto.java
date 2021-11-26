package com.boba.bobabuddy.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Objects matching the corresponding entities in the domain layer
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",
        scope = StoreDto.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "stores", itemRelation = "store")
public class StoreDto implements RatableObjectDto {

    private UUID id;
    private String name;
    private String location;
    private String owner;

    @JsonIdentityReference
    private List<SimpleItemDto> menu;

    @JsonIdentityReference
    private Set<SimpleRatingDto> ratings;

    public StoreDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<SimpleItemDto> getMenu() {
        return menu;
    }

    public void setMenu(List<SimpleItemDto> menu) {
        this.menu = menu;
    }

    public Set<SimpleRatingDto> getRatings() {
        return ratings;
    }

    public void setRatings(Set<SimpleRatingDto> ratings) {
        this.ratings = ratings;
    }
}
