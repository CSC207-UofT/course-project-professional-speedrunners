package com.boba.bobabuddy.infrastructure.database;

import com.boba.bobabuddy.core.entity.Store;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreJpaRepository extends RatableObjectJpaRepository<Store> {
    List<Store> findByLocation(String location);
}
