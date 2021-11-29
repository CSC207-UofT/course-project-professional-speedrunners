package com.boba.bobabuddy.core.usecase.category.impl;

import com.boba.bobabuddy.core.usecase.category.CreateCategoryService;
import com.boba.bobabuddy.infrastructure.dao.CategoryJpaRepository;
import com.boba.bobabuddy.infrastructure.dto.CategoryDto;
import com.boba.bobabuddy.core.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class handle the usecase of creating category and adding it into the system
 */

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCategoryServiceImpl implements CreateCategoryService{
    private final CategoryJpaRepository repo;

    @Override
    public Category create(CategoryDto category){
        Category createdCategory = Category.builder()
                .name(category.getName())
                .build();
        return repo.save(createdCategory);
    }

}
