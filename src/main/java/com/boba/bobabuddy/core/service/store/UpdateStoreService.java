package com.boba.bobabuddy.core.service.store;

import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface UpdateStoreService {
    /**
     * Update a store by overwriting it.
     * The api user is responsible for sending in a Store representation that was modified.
     * However, if no Store with the same uuid exist and exception will be thrown.
     *
     * @param storeToUpdate Store to update.
     * @param storePatch    the same store with updated fields
     * @return the updated store.
     * @throws DifferentResourceException thrown when storePatch have a different id than the storeToUpdate
     */
    Store updateStore(Store storeToUpdate, StoreDto storePatch) throws DifferentResourceException;

    /**
     * Adding an Item to a Store
     *
     * @param store the Store entity to append Item to
     * @param item  the Item to be appended
     * @return the mutated store
     * @throws DuplicateResourceException thrown when the Item already exist within the Store
     */
    Store addItem(Store store, Item item) throws DuplicateResourceException;

    /**
     * Removing an Item from a Store
     *
     * @param store the Store to be mutated
     * @param item  the Item to be removed
     * @return the mutated store
     * @throws ResourceNotFoundException thrown when the store does not contain the item
     */
    Store removeItem(Store store, Item item) throws ResourceNotFoundException;

    /**
     * Update a store's image
     * @param id id of the store
     * @param imageURL URL of the image
     * @return updated store
     * @throws IOException is thrown when store's image cannot be updated
     */
    Store updateStoreImage(UUID id, String imageURL) throws IOException;
}
