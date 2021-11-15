package com.boba.bobabuddy.core.usecase.item;


import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.item.port.ICreateItem;
import com.boba.bobabuddy.core.usecase.store.port.IFindStore;
import com.boba.bobabuddy.core.usecase.store.port.IUpdateStore;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

/**
 * This class handle the usecase of creating item and adding it into the system.
 */

@Service
@Transactional // Reverts any database modification when an exception is thrown before a method call finishes
public class CreateItem implements ICreateItem {

    private final ItemJpaRepository repo;
    private final IFindStore findStore;
    private final IUpdateStore updateStore;

    /***
     * Initialize the Create Item usecase by injecting it with required dependencies.
     *
     * @param repo a Data Access Object for handling item data
     * @param findStore the findStore usecase for linking association
     * @param updateStore the UpdateStore usecase for linking association
     */
    public CreateItem(ItemJpaRepository repo, IFindStore findStore, IUpdateStore updateStore) {
        this.repo = repo;
        this.findStore = findStore;
        this.updateStore = updateStore;
    }

    /***
     * Create an item and save it to the database
     * Note that the actual building of an Item object is handled by the CreateItemRequest
     * wrapper which help with converting JSON request into Entity objects via HTTPMessageConverter.
     * TODO: Need Testing
     *     JpaRepository.save not only add new entity into the database, if the entity passed to the
     *     method already exists, it will also overwrite the existing entity with new data.
     *     this method should not be allowed to do this since it will be called with a HTTP POST
     *     request instead of PUT or PATCH.
     *     This should not happen since the controller constructs a CreateItemRequest object, which constructs
     *     a Item object with random UUID. But testing will be required.
     * @param item Item to be persisted in the database.
     * @param storeId storeId associated to the store selling this item
     * @return Item object saved in the database
     * @throws DuplicateResourceException Thrown when the item already exist in the store
     * @throws ResourceNotFoundException Thrown when the store to link to was not found
     */
    @Override
    public Item create(Item item, UUID storeId) throws DuplicateResourceException, ResourceNotFoundException {
        Store store = findStore.findById(storeId);
        item.setStore(store);
        item.setRatings(new HashSet<>());
        item = repo.save(item);
        updateStore.addItem(store, item);
        return item;
    }
}
