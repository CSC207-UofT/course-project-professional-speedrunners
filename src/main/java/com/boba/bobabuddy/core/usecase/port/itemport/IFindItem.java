package com.boba.bobabuddy.core.usecase.port.itemport;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.item.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component

public interface IFindItem {
    List<Item> findByStore(UUID id);

    Item findById(UUID id) throws ItemNotFoundException;

    List<Item> findAll();

    List<Item> findByName(String name);

    List<Item> findByNameContaining(String name);

    List<Item> findByPriceLessThanEqual(float price);

    List<Item> findByAvgRatingGreaterThanEqual(float rating);

}
