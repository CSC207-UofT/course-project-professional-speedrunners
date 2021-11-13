package com.boba.bobabuddy.infrastructure.database;

import com.boba.bobabuddy.core.entity.Rating;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * The Database Gateway as defined by Spring Data JPA
 * by extending JpaRepository we can create derived query methods that fits our need.
 * Once defined, SpringBoot will automatically generate a JPA gateway implementation that supports these
 * custom queries for us and inject it to services using this interface.
 * refer to Spring Data JPA documentation for details.
 */

@Repository
public interface RatingJpaRepository extends GenericResourceJpaRepository<Rating, UUID> {
    Set<Rating> findByRatableObject_id(UUID id);

    Set<Rating> findByUser_email(String email);

}
