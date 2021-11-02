package com.boba.bobabuddy.infrastructure.database;

import com.boba.bobabuddy.core.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface StoreJpaRepository extends JpaRepository<Store, UUID> {
    Optional<Store> findById(UUID id);

    List<Store> findByName(String name);

    List<Store> findByLocation(String location);

    List<Store> findByAvgRatingGreaterThanEqual(float rating);

    Store removeStoreById(UUID id);
}
