package com.boba.bobabuddy.core.service.item;

import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;

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
     * @param itemToUpdate Item to update.
     * @param newItem      the overwriting item.
     * @return the updated item.
     * @throws DifferentResourceException thrown when itemPatch have a different id than the itemToUpdate
     */
    Item updateItem(UUID itemId, ItemDto newItem) throws DifferentResourceException;
}
