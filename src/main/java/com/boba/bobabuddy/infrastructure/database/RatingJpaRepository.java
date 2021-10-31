package com.boba.bobabuddy.infrastructure.database;

import com.boba.bobabuddy.core.entity.RatingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatingJpaRepository extends JpaRepository<RatingPoint, UUID> {
    List<RatingPoint> findByRatableObject_id(UUID id);

    List<RatingPoint> findByUser_id(UUID id);

    RatingPoint removeByRatingPoint_id(UUID id);
}
