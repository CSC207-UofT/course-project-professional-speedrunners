package com.boba.bobabuddy.core.usecase.port.IRequest.IItemIn;

import com.boba.bobabuddy.core.entity.Item;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component

public interface IFindItem {
    List<Item> findByStore(UUID id);

    Optional<Item> findById(UUID id);

    List<Item> findAll();

    List<Item> findByName(String name);

    List<Item> findByNameContaining(String name);

    List<Item> findByPriceLessThanEqual(float price);

    List<Item> findByAvgRatingGreaterThanEqual(float rating);

}
