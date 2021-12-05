package com.boba.bobabuddy.core.data.dao;

import com.boba.bobabuddy.core.domain.Category;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CategoryJpaRepository extends GenericResourceJpaRepository<Category, UUID>{
    Category findByNameIgnoringCase(String name);

    List<Category> findByNameIsIn(Set<String> categoryNames, Sort sort);

}
