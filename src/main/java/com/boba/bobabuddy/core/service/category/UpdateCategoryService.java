package com.boba.bobabuddy.core.service.category;

import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

public interface UpdateCategoryService {
    /**
     * Adds category to item
     *
     * @param categoryToUpdate category to update
     * @param itemToAdd item to add
     * @return updated category
     * @throws DuplicateResourceException if category already contains this item
     */
    Category addItemToCategory(Category categoryToUpdate, Item itemToAdd) throws DuplicateResourceException;

    /**
     * Removes a category from item
     *
     * @param categoryToUpdate category to update
     * @param itemToRemove item to remove
     * @return updated category
     * @throws ResourceNotFoundException if category does not contain this item
     */
    Category removeItemFromCategory(Category categoryToUpdate, Item itemToRemove) throws ResourceNotFoundException;
}
