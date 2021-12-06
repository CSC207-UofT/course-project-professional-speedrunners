package com.boba.bobabuddy.core.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Objects matching the corresponding entities in the domain layer
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor @NoArgsConstructor
@Jacksonized @Builder
public class ItemDto {
    private StoreDto store;
    private Set<RatingDto> ratings;
    private String name;
    private String imageUrl;
    private double price;
    private UUID id;
    private double avgRating;
    private List<CategoryDto> categories;
}
