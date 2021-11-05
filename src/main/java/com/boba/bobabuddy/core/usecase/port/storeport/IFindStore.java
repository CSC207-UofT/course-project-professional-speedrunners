package com.boba.bobabuddy.core.usecase.port.storeport;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.store.exceptions.StoreNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component

public interface IFindStore {
    List<Store> findAll();

    Store findByLocation(String location);

    List<Store> findByNameContaining(String name);

    Store findById(UUID id) throws StoreNotFoundException;

    Store findByName(String name);

    List<Store> findByAvgRatingGreaterThanEqual(float rating, boolean sorted);
}
