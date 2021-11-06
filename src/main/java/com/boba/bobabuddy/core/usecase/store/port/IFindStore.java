package com.boba.bobabuddy.core.usecase.store.port;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component

public interface IFindStore {
    List<Store> findAll();

    List<Store> findByLocation(String location);

    List<Store> findByNameContaining(String name);

    Store findById(UUID id) throws ResourceNotFoundException;

    List<Store> findByName(String name);

    List<Store> findByAvgRatingGreaterThanEqual(float rating, boolean sorted);

    Store findByItem(UUID id) throws ResourceNotFoundException;

    Store findByRating(UUID id) throws ResourceNotFoundException;
}
