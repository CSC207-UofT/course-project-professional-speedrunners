package com.boba.bobabuddy.core.service.item;

import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface RemoveItemService {
    /**
     * removes an item from database that has the matching itemId.
     *
     * @param id id of the Item
     * @throws ResourceNotFoundException when no item with the associated id was found
     */
    void removeById(UUID id) throws ResourceNotFoundException;
}
