package com.boba.bobabuddy.core.service.category.impl;

import com.boba.bobabuddy.core.data.dao.CategoryJpaRepository;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.category.FindCategoryService;
import com.boba.bobabuddy.core.service.category.RemoveCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class handle the usecase of removing categories in the system.
 */
@Service("RemoveCategoryService")
@Transactional
public class RemoveCategoryServiceImpl implements RemoveCategoryService{

    private final CategoryJpaRepository repo;
    private final FindCategoryService findCategory;

    /**
     * Initialize RemoveCategory usecase by injecting dependencies
     *
     * @param repo DAO for handling category data
     * @param findCategory FindCategory usecase
     */
    public RemoveCategoryServiceImpl(final CategoryJpaRepository repo, FindCategoryService findCategory){
        this.repo = repo;
        this.findCategory = findCategory;
    }

    public void removeById(UUID id, UUID userId) throws ResourceNotFoundException {
        Category category = findCategory.findById(id);
        repo.delete(category);
    }
}
