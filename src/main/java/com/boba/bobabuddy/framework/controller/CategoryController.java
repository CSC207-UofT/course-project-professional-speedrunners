package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.data.dto.CategoryDto;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.service.category.CreateCategoryService;
import com.boba.bobabuddy.core.service.category.RemoveCategoryService;
import com.boba.bobabuddy.core.service.category.FindCategoryService;
import com.boba.bobabuddy.framework.converter.DtoConverter;
import com.boba.bobabuddy.framework.converter.SortQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for Category related api calls
 */
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CreateCategoryService createCategory;
    private final RemoveCategoryService removeCategory;
    private final FindCategoryService findCategory;
    private final DtoConverter<Category, CategoryDto> converter;

    /**
     * Post HTTP requests for creating category resource
     *
     * @param createCategoryRequest Request class that contains data necessary to construct a Category entity.
     * @param userId id of the user to ensure only admin can create category
     * @return Category that was constructed, which will be automatically converted to JSON and send it to the caller.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/categories")
    @PreAuthorize("@FindUserService.findById(#userId).getEmail() == authentication.principal.username || hasAuthority('ROLE_ADMIN')")
    public CategoryDto createCategory(@RequestBody CategoryDto createCategoryRequest, @RequestParam("userId") UUID userId){
        return converter.convertToDto(createCategory.create(createCategoryRequest, userId));
    }

    /**
     * Root endpoint for category resources
     *
     * @return collection fo categories exist in the database
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/categories")
    public List<CategoryDto> findAll(@RequestParam(defaultValue = "unsorted") String sortBy) {
        return converter.convertToDtoList(findCategory.findAll(SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for a category resource with matching id.
     *
     * @param id the primary uuid key of the resource
     * @return the category resource with matching UUID
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/categories/{id}")
    public CategoryDto findById(@PathVariable UUID id){
        return converter.convertToDto(findCategory.findById(id));
    }

    /**
     * Handles GET request for a category resources that have a certain item
     *
     * @param id id of the item resource
     * @param sortBy sort the returned list
     * @return a list of category resources that have the specified item
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/categories", params = "itemId")
    public List<CategoryDto> findByItem(@RequestParam("itemId") UUID id, @RequestParam(defaultValue = "unsorted")String sortBy){
        return converter.convertToDtoList(findCategory.findByItem(id, SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for category resources that have matching name
     *
     * @param name name of required category
     * @return a category resource with the matching name
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/categories", params = "name")
    public CategoryDto findByName(@RequestParam("name") String name){
        return converter.convertToDto(findCategory.findByName(name));
    }

    /**
     * Handles DELETE request to delete a category resource from the system
     *
     * @param id id of the resource to be deleted
     * @param userId id of the user to ensure only admins can delete a category
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/categories/{id}")
    @PreAuthorize("@FindUserService.findById(#userId).getEmail() == authentication.principal.username || hasAuthority('ROLE_ADMIN')")
    public void removeCategory(@PathVariable UUID id, @RequestParam("userId") UUID userId){
        removeCategory.removeById(id, userId);
    }

}
