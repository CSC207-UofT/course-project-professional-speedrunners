package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.store.port.IUpdateStore;
import com.boba.bobabuddy.infrastructure.database.StoreJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * This class handle the usecase of updating stores in the system.
 */

@Service

@Transactional
public class UpdateStore implements IUpdateStore {
    private final StoreJpaRepository repo;

    /***
     Construct the usecase class
     @param repo DAO for Store entity.
     */
    @Autowired
    public UpdateStore(final StoreJpaRepository repo) {
        this.repo = repo;
    }

    /***
     * Update a store by overwriting it.
     * The api user is responsible for sending in a Store representation that was modified.
     * However, if no Store with the same uuid exist and exception will be thrown.
     * @param storeToUpdate Store to update.
     * @param storePatch the same store with updated fields
     * @return the updated store.
     * @throws DifferentResourceException thrown when storePatch have a different id than the storeToUpdate
     */
    @Override
    public Store updateStore(Store storeToUpdate, Store storePatch) throws DifferentResourceException {
        if (Objects.equals(storeToUpdate, storePatch)) {
            return repo.save(storePatch);
        }
        throw new DifferentResourceException("Not the same store");
    }

    /***
     * Adding an Item to a Store
     * @param store the Store entity to append Item to
     * @param item the Item to be appended
     * @return the mutated store
     * @throws DuplicateResourceException thrown when the Item already exist within the Store
     */
    @Override
    public Store addItem(Store store, Item item) throws DuplicateResourceException {
        if (store.addItem(item)) return repo.save(store);
        throw new DuplicateResourceException("Item already in store");
    }

    /***
     * Removing an Item from a Store
     * @param store the Store to be mutated
     * @param item the Item to be removed
     * @return the mutated store
     * @throws ResourceNotFoundException thrown when the store does not contain the item
     */
    @Override
    public Store removeItem(Store store, Item item) throws ResourceNotFoundException {
        if (store.removeItem(item)) return repo.save(store);
        throw new ResourceNotFoundException("No such Item", new Exception());
    }
}
