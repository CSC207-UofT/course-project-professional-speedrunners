package com.boba.bobabuddy.infrastructure.dto;

import com.boba.bobabuddy.core.entity.Rating;

import java.util.Set;
import java.util.UUID;

public interface RatableObjectDto extends BaseRatableObjectDto{
    void setRatings(Set<SimpleRatingDto> ratings);
}
