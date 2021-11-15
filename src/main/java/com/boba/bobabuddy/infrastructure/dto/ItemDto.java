package com.boba.bobabuddy.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.hateoas.server.core.Relation;

import java.util.Set;
import java.util.UUID;

/**
 * Class that represents an Item in the domain layer
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = ItemDto.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "items", itemRelation = "item")
public class ItemDto extends SimpleItemDto implements RatableObjectDto{

    @JsonIdentityReference
    private
    StoreDto store;
    @JsonIdentityReference
    private Set<SimpleRatingDto> ratings;

    public ItemDto() {
    }

    public ItemDto(String name, float price, float avgRating) {
        super(name, price, avgRating);
    }

    public Set<SimpleRatingDto> getRatings() {
        return ratings;
    }

    public void setRatings(Set<SimpleRatingDto> ratings) {
        this.ratings = ratings;
    }

    public StoreDto getStore() {
        return store;
    }

    public void setStore(StoreDto store) {
        this.store = store;
    }
}
