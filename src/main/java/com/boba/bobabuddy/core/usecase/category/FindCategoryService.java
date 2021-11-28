package com.boba.bobabuddy.core.usecase.category;

import com.boba.bobabuddy.core.entity.Category;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface FindCategoryService {
    Category findByName(String name);

    Set<Category> findByItem(UUID id);

    List<Category> findAll();
}
