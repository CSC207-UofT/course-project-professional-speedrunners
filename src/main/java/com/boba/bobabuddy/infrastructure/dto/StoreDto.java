package com.boba.bobabuddy.infrastructure.dto;

import com.boba.bobabuddy.infrastructure.JpaEntityResolver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Class that represents a Store in the domain layer
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",
        scope = StoreDto.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreDto implements RatableObjectDto {

    private UUID id;
    private String name;
    private String location;

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
