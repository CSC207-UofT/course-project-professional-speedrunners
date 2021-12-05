package com.boba.bobabuddy.core.service.category;

import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.data.dto.CategoryDto;

import java.util.UUID;


public interface CreateCategoryService {
    /**
     * Create a category and save it to the database
     *
     * @param category: category to be persisted in the database
     * @return the Category object that was persisted into the database.
     */

    Category create(CategoryDto category, UUID userId);

}
