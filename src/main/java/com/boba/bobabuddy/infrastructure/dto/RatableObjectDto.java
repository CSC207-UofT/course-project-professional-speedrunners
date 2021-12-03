package com.boba.bobabuddy.infrastructure.dto;

import java.util.Set;
/**
 * Data Transfer Objects matching the corresponding entities in the domain layer
 */
public interface RatableObjectDto extends BaseRatableObjectDto {
    void setRatings(Set<SimpleRatingDto> ratings);
}
