package com.boba.bobabuddy.infrastructure.dao;

import com.boba.bobabuddy.core.entity.Store;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreJpaRepository extends RatableObjectJpaRepository<Store> {
    List<Store> findByLocation(String location);

    Optional<Store> findByMenu_Id(UUID id);

    Optional<Store> findById(UUID id);

    List<Store> findByNameContaining(String name);

    List<Store> findByAvgRatingGreaterThanEqual(float rating, Sort sort);
}
