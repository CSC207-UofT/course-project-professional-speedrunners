package com.boba.bobabuddy.core.service.category;

import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

/**
 * Usecase Input Boundary
 */

public interface FindCategoryService {
    /**
     * Find Category by its name. Have to be fully matching
     *
     * @param name name to be matched
     * @return Category with matching name.
     * @throws ResourceNotFoundException thrown when no such category exist.
     */
    Category findByName(String name) throws ResourceNotFoundException;

    /**
     * Find Category by its name. Also do partial match
     *
     * @param name name to be matched
     * @param sort sor the returned list
     * @return Category with matching name.
     */
    List<Category> findByNameContaining(String name, Sort sort);


    /**
     * Find categories that contain the specified item.
     *
     * @param id id of the item entity
     * @param sort sort the categories
     * @return a list of categories that have the specifies item
     * @throws ResourceNotFoundException thrown when no such category exist.
     */
    List<Category> findByItem(UUID id, Sort sort) throws ResourceNotFoundException;

    /**
     * Find all categories that exists in the database
     *
     * @param sort sort the categories
     * @return list of all categories in teh database, or an empty list if the database is empty
     */
    List<Category> findAll(Sort sort);

    /**
     * Find categories that contain the specified item.
     *
     * @param id id of the category entity
     * @return a category with the matching id
     * @throws ResourceNotFoundException thrown when no such category exist.
     */
    Category findById(UUID id) throws ResourceNotFoundException;
}
