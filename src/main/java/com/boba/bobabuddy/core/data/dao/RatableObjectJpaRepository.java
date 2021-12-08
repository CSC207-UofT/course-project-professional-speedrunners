package com.boba.bobabuddy.core.data.dao;

import com.boba.bobabuddy.core.domain.RatableObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * DAO for Ratable object type. Query will be made on both chile types (item, store)
 */

@Repository
public interface RatableObjectJpaRepository<T extends RatableObject> extends GenericResourceJpaRepository<T, UUID> {

    List<T> findByNameIgnoreCase(String name, Sort sort);

    List<T> findByNameContainingIgnoreCase(String name, Sort sort);

    // Note that using native query is a workaround since the custom query must follow the SQL flavor of the database
    // implementation (H2 in our case), instead of having the query language translated dynamically by spring data jpa
    @Query(value = "select * from RATABLE_OBJECT where TYPE = ?1 AND ID = ?2", nativeQuery = true)
    Optional<T> findByTypeAndId(String type, UUID id);

    List<T> findByAvgRatingGreaterThanEqual(double rating, Sort sort);

    Optional<T> findByRatings_id(UUID id);

}
