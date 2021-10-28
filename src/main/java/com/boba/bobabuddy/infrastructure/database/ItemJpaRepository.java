package com.boba.bobabuddy.infrastructure.database;

import com.boba.bobabuddy.core.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemJpaRepository extends JpaRepository<Item, UUID>{
    List<Item> findByStore_id(UUID id);

    List<Item> findByName(String name);

    List<Item> findByNameContaining(String name);

    List<Item> findByPriceLessThanEqual(float price);

    List<Item> findByAvgRatingGreaterThanEqual(float rating);

    Item removeById(UUID id);
}
