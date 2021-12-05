package com.boba.bobabuddy.core.service.category.impl;

import com.boba.bobabuddy.core.data.dao.CategoryJpaRepository;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.category.UpdateCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class handle the usecase of updating categories in the system.
 */
@Service("UpdateCategoryService")
@Transactional
public class UpdateCategoryServiceImpl implements UpdateCategoryService {
    private final CategoryJpaRepository repo;

    /***
     * Construct the usecase class
     * @param repo the repository that hosts the Category entity.
     */
    public UpdateCategoryServiceImpl(CategoryJpaRepository repo) {
        this.repo = repo;
    }

    public Category addItemToCategory(Category categoryToUpdate, Item itemToAdd) throws DuplicateResourceException{
        if (categoryToUpdate.addItem(itemToAdd)){
            return repo.save(categoryToUpdate);
        }
        throw new DuplicateResourceException("This category already contains this item");
    }

    public Category removeItemFromCategory(Category categoryToUpdate, Item itemToRemove) throws ResourceNotFoundException{
        if (categoryToUpdate.removeItem(itemToRemove)){
            return repo.save(categoryToUpdate);
        }
        throw new ResourceNotFoundException("This category does not contain this item");
    }
}
