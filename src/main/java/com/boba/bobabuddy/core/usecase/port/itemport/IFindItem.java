package com.boba.bobabuddy.core.usecase.port.itemport;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component

public interface IFindItem {
    List<Item> findByStore(UUID id);

    Item findById(UUID id) throws ResourceNotFoundException;

    List<Item> findAll();

    List<Item> findByName(String name);

    List<Item> findByNameContaining(String name);

    List<Item> findByPriceLessThanEqual(float price, boolean sorted);

    List<Item> findByAvgRatingGreaterThanEqual(float rating, boolean sorted);
}
