package com.boba.bobabuddy.core.data.dao;

import com.boba.bobabuddy.core.domain.Store;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Repository
public interface StoreJpaRepository extends RatableObjectJpaRepository<Store> {
    List<Store> findByLocation(String location);

    List<Store> findByAvgRatingGreaterThanEqual(@Min(value = 0) @Max(value = 1) double avgRating, Sort sort);
}
