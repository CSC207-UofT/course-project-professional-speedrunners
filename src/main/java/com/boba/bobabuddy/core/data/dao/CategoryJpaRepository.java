package com.boba.bobabuddy.core.data.dao;

import com.boba.bobabuddy.core.domain.Category;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * DAO for category resources
 */
@Repository
public interface CategoryJpaRepository extends GenericResourceJpaRepository<Category, UUID>{
    Category findByNameIgnoringCase(String name);

    List<Category> findByItems_id(UUID itemId, Sort sort);

    List<Category> findByNameContainingIgnoreCase(String name, Sort sort);
}
