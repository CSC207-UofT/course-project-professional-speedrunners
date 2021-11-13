package com.boba.bobabuddy.core.usecase.rating.port;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IFindRating {
    Set<Rating> findByRatableObject(UUID id) throws ResourceNotFoundException;

    Set<Rating> findByUser(String email);

    Rating findById(UUID id) throws ResourceNotFoundException;

    List<Rating> findAll();
}
