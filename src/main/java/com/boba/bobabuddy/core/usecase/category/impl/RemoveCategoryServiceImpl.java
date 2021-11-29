package com.boba.bobabuddy.core.usecase.category.impl;

import com.boba.bobabuddy.infrastructure.dao.CategoryJpaRepository;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.category.FindCategoryService;
import com.boba.bobabuddy.core.usecase.category.RemoveCategoryService;
import com.boba.bobabuddy.core.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class handle the usecase of removing categories in the system.
 */
@Service
@Transactional
public class RemoveCategoryServiceImpl implements RemoveCategoryService{

    private final CategoryJpaRepository repo;
    private final FindCategoryService findCategory;

    /**
     * Initialize RemoveCategory usecase by injecting dependecncies
     *
     * @param repo DAO for handling category data
     * @param findCategory FindCategory usecase
     */
    //TODO: do i really need autowired here? constructor injection should also work (what i have here)
    public RemoveCategoryServiceImpl(final CategoryJpaRepository repo, FindCategoryService findCategory){
        this.repo = repo;
        this.findCategory = findCategory;
    }

    public void removeById(UUID id) throws ResourceNotFoundException{
        Category category = findCategory.findById(id);
        repo.delete(category);
    }
}
