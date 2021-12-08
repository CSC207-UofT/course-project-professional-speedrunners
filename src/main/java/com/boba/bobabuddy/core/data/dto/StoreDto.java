package com.boba.bobabuddy.core.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.*;

/**
 * Data Transfer Objects matching the corresponding entities in the domain layer
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor @NoArgsConstructor
@Jacksonized @Builder
public class StoreDto {
    private UUID id;
    private String name;
    private String location;
    private String imageUrl;
    @Builder.Default
    private List<ItemDto> menu = new ArrayList<>();
    private String owner;
    @Builder.Default
    private Set<RatingDto> ratings = new HashSet<>();
    private double avgRating;

    public boolean addItem(ItemDto item){
        return menu.add(item);
    }

    public boolean addRating(RatingDto ratingDto) {
        return ratings.add(ratingDto);
    }
}
