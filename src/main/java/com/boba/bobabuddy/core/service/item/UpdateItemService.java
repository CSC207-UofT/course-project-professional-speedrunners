package com.boba.bobabuddy.core.service.item;

import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface UpdateItemService {
    /**
     * Update an Item by overwriting it.
     * If there exists an Item entity within the database that has the same uuid, data will be overwritten and thus
     * updated.
     * However, if no Item with the same uuid exist an exception will be thrown.
     *
     * @param itemId        UUId of item to update
     * @param newItem      the overwriting item.
     * @return the updated item.
     * @throws DifferentResourceException thrown when itemPatch have a different id than the itemToUpdate
     */
    Item updateItem(UUID itemId, ItemDto newItem) throws DifferentResourceException;

    /**
     * Update an item but just its price.
     *
     * @param itemId UUId of item to update
     * @param price the new price
     * @return the updated Item
     * @throws IllegalArgumentException if the new price is less than 0
     */
    Item updateItemPrice(UUID itemId, double price) throws IllegalArgumentException;

    /**
     * Adds category to item
     *
     * @param itemId UUID of item to update
     * @param categoryName name of category to add
     * @return updated item
     * @throws DuplicateResourceException if item already contains this category
     */
    Item addCategory(UUID itemId, String categoryName) throws DuplicateResourceException;

    /**
     * Removes a category from item
     *
     * @param itemId UUID of item to update
     * @param categoryName name of category to remove
     * @return updated item
     * @throws ResourceNotFoundException if item does not contain this category
     */
    Item removeCategory(UUID itemId, String categoryName) throws ResourceNotFoundException;


    /**
     * Update an item's image
     * @param id id of the item
     * @param imageURL URL of the image
     * @return updated item
     */
    Item updateItemImage(UUID id, String imageURL);
}
