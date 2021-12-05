package com.boba.bobabuddy.core.service.category;

import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

public interface UpdateCategoryService {
    Category addItemToCategory(Category categoryToUpdate, Item itemToAdd) throws DuplicateResourceException;

    Category removeItemFromCategory(Category categoryToUpdate, Item itemToRemove) throws ResourceNotFoundException;
}
