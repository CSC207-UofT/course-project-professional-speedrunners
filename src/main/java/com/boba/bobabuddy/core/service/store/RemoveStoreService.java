package com.boba.bobabuddy.core.service.store;

import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface RemoveStoreService {
    /**
     * Removes a store from database that has the matching storeId.
     *
     * @param id id of the store.
     * @throws ResourceNotFoundException thrown when store was not found
     */
    void removeById(UUID id) throws ResourceNotFoundException;
}
