package com.boba.bobabuddy.infrastructure.dao;

import com.boba.bobabuddy.core.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryJpaRepository extends GenericResourceJpaRepository<Category, Long>{
    Category findByName(String name);

}
