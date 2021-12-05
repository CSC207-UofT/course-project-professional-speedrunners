package com.boba.bobabuddy.core.service.category.impl;

import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.service.category.CreateCategoryService;
import com.boba.bobabuddy.core.data.dao.CategoryJpaRepository;
import com.boba.bobabuddy.core.data.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class handle the usecase of creating category and adding it into the system
 */

@Service("CreateCategoryService")
@Transactional
@RequiredArgsConstructor
public class CreateCategoryServiceImpl implements CreateCategoryService{
    private final CategoryJpaRepository repo;

    @Override
    public Category create(CategoryDto category, UUID userid){
        Category createdCategory = Category.builder()
                .name(category.getName())
                .build();
        return repo.save(createdCategory);
    }
}
