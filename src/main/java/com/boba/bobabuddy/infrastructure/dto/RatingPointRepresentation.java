package com.boba.bobabuddy.infrastructure.dto;

import com.boba.bobabuddy.core.entity.RatableObject;

import java.util.UUID;

public class RatingPointRepresentation {
    private final int rating;
    private final UserRepresentation user;
    private final RatableObjectRepresentation<RatableObject> ratableObject;
    private final UUID id;


}
