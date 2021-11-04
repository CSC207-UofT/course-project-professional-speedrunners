package com.boba.bobabuddy.infrastructure.database;

import com.boba.bobabuddy.core.entity.Item;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/***
 * The Database Gateway as defined by Spring Data JPA
 * by extending JpaRepository we can create derived query methods that fits our need.
 * Once defined, SpringBoot will automatically generate a JPA gateway implementation that supports these
 * custom queries for us and inject it to services using this interface.
 * refer to Spring Data JPA documentation for details.
 */
@Repository
public interface ItemJpaRepository extends RatableObjectJpaRepository<Item> {
    List<Item> findByStore_id(UUID id);

    List<Item> findByPriceLessThanEqual(float price, Sort sort);

}
