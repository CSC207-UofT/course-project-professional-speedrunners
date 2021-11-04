package com.boba.bobabuddy.infrastructure.database;

import com.boba.bobabuddy.core.entity.RatableObject;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatableObjectJpaRepository<T extends RatableObject> extends GenericResourceJpaRepository<T, UUID> {

    List<T> findByName(String name);

    List<T> findByNameContaining(String name);

    List<T> findByAvgRatingGreaterThanEqual(float rating, Sort sort);


}
