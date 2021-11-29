package com.boba.bobabuddy.core.usecase.category;

import com.boba.bobabuddy.core.entity.Category;
import com.boba.bobabuddy.infrastructure.dto.CategoryDto;


public interface CreateCategoryService {
    /**
     * Create a category and save it to the database
     *
     * @param category: category to be persisted in the database
     * @return the Category object that was persisted into the database.
     */

    Category create(CategoryDto category);
}
