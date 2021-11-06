package com.boba.bobabuddy.core.usecase.item;


import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.item.port.IFindItem;
import com.boba.bobabuddy.core.usecase.item.port.IRemoveItem;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class handle the usecase of removing items in the system.
 */

@Service
@Transactional
public class RemoveItem implements IRemoveItem {

    private final ItemJpaRepository repo;
    private final IFindItem findItem;

    /**
     * Initialize RemoveItem usecase by injecting dependencies
     *
     * @param repo     DAO for handling item data
     * @param findItem FindItem usecase to find the item to be removed
     */
    public RemoveItem(final ItemJpaRepository repo, IFindItem findItem) {
        this.repo = repo;
        this.findItem = findItem;
    }

    /***
     * removes an item from database that has the matching itemId.
     * @param id id of the Item
     * @throws ResourceNotFoundException when no item with the associated id was found
     */
    @Override
    public void removeById(UUID id) throws ResourceNotFoundException {
        Item item = findItem.findById(id);
        repo.delete(item);
    }
}
