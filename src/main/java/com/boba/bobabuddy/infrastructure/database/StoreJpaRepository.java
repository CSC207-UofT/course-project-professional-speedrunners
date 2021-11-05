package com.boba.bobabuddy.infrastructure.database;

import com.boba.bobabuddy.core.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface StoreJpaRepository extends RatableObjectJpaRepository<Store> {
    List<Store> findByLocation(String location);

    Optional<Store> findById(UUID id);

    Store findByName(String name);

    Store findByLocation(String location);

    List<Store> findByNameContaining(String name);

    List<Store> findByAvgRatingGreaterThanEqual(float rating, Sort sort);

    Store removeById(UUID id);
}
