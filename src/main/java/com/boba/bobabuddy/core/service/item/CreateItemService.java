package com.boba.bobabuddy.core.service.item;

import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface CreateItemService {
    /**
     * Create an item and save it to the database
     * Note that the actual building of an Item object is handled by the CreateItemRequest
     * wrapper which help with converting JSON request into Entity objects via HTTPMessageConverter.
     *
     * @param item    Item to be persisted in the database.
     * @param storeId storeId associated to the store selling this item
     * @return Item object saved in the database
     * @throws DuplicateResourceException Thrown when the item already exist in the store
     * @throws ResourceNotFoundException  Thrown when the store to link to was not found
     */
    Item create(ItemDto item, UUID storeId) throws DuplicateResourceException, ResourceNotFoundException;

}
