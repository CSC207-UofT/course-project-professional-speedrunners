package com.boba.bobabuddy.core.service.item.impl;

import com.boba.bobabuddy.core.data.dao.ItemJpaRepository;
import com.boba.bobabuddy.core.data.dto.CategoryDto;
import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.category.CreateCategoryService;
import com.boba.bobabuddy.core.service.category.FindCategoryService;
import com.boba.bobabuddy.core.service.category.RemoveCategoryService;
import com.boba.bobabuddy.core.service.item.FindItemService;
import com.boba.bobabuddy.core.service.item.UpdateItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class handle the usecase of updating items in the system.
 */
@Service("UpdateItemService")
@Transactional
public class UpdateItemServiceImpl implements UpdateItemService {
    private final ItemJpaRepository repo;
    private final FindItemService findItemService;
    private final FindCategoryService findCategoryService;
    private final CreateCategoryService createCategoryService;
    private final RemoveCategoryService removeCategoryService;

    /***
     * Construct the usecase class
     * @param repo the repository that hosts the Item entity.
     * @param findItemService Find item service to look for the item to update
     * @param findCategoryService Finds category to add to item
     * @param createCategoryService Creates new category
     * @param removeCategoryService Remove the category
     */
    public UpdateItemServiceImpl(final ItemJpaRepository repo, FindItemService findItemService,
                                 FindCategoryService findCategoryService,
                                 CreateCategoryService createCategoryService,
                                 RemoveCategoryService removeCategoryService) {
        this.repo = repo;
        this.findItemService = findItemService;
        this.findCategoryService = findCategoryService;
        this.createCategoryService = createCategoryService;
        this.removeCategoryService = removeCategoryService;
    }

    @Override
    public Item updateItem(UUID itemId, ItemDto itemPatch) throws DifferentResourceException {
        Item itemToUpdate = findItemService.findById(itemId);

        itemToUpdate.setName(itemPatch.getName());
        itemToUpdate.setPrice(itemPatch.getPrice());

        return repo.save(itemToUpdate);
    }

    @Override
    public Item updateItemPrice(UUID itemId, float price) throws IllegalArgumentException{
        if (price < 0) {
            throw new IllegalArgumentException("Price less than 0.");
        }
        Item itemToUpdate = findItemService.findById(itemId);
        itemToUpdate.setPrice(price);
        return repo.save(itemToUpdate);
    }

    @Override
    public Item addCategory(UUID itemId, String categoryName) throws DuplicateResourceException{
        Item itemToUpdate = findItemService.findById(itemId);
        Category categoryToAdd;

        try{
            categoryToAdd = findCategoryService.findByName(categoryName);
        }
        catch (ResourceNotFoundException e){
            categoryToAdd = createCategoryService.create(new CategoryDto());
            categoryToAdd.setName(categoryName);
        }

        if (itemToUpdate.addCategory(categoryToAdd)){
            categoryToAdd.addItem(itemToUpdate);
            return repo.save(itemToUpdate);
        }
        throw new DuplicateResourceException("This item already contains this category");
    }

    @Override
    public Item removeCategory(UUID itemId, String categoryName) throws ResourceNotFoundException{
        Item itemToUpdate = findItemService.findById(itemId);
        Category categoryToRemove = findCategoryService.findByName(categoryName);

        if (itemToUpdate.removeCategory(categoryToRemove)){
            categoryToRemove.removeItem(itemToUpdate);
            if (categoryToRemove.getItems().isEmpty()){
                removeCategoryService.removeById(categoryToRemove.getId());
            }
            return repo.save(itemToUpdate);
        }
        throw new ResourceNotFoundException("This item does not contain this category");

    }



}
