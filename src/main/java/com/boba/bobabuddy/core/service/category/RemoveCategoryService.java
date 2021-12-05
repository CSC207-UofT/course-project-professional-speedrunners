package com.boba.bobabuddy.core.service.category;

import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.UUID;

/**
 * Usecase Input Boundary
 */

public interface RemoveCategoryService {
    /**
     * Removes a category from database that has the matching categoryID.
     *
     * @param id id of the category
     * @throws ResourceNotFoundException thrown when category was not found
     */

    void removeById(UUID id, UUID userId) throws ResourceNotFoundException;

}
