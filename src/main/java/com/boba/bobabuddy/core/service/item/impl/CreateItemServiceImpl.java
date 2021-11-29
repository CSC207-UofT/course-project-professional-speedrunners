package com.boba.bobabuddy.core.service.item.impl;


import com.boba.bobabuddy.core.data.dao.ItemJpaRepository;
import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.item.CreateItemService;
import com.boba.bobabuddy.core.service.store.FindStoreService;
import com.boba.bobabuddy.core.service.store.UpdateStoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


/**
 * This class handle the usecase of creating item and adding it into the system.
 */
@Service("CreateItemService")
@Transactional
public class CreateItemServiceImpl implements CreateItemService {

    private final ItemJpaRepository repo;
    private final FindStoreService findStore;
    private final UpdateStoreService updateStore;

    /**
     * Initialize the Create Item usecase by injecting it with required dependencies.
     *
     * @param repo        a Data Access Object for handling item data
     * @param findStore   the findStore usecase for linking association
     * @param updateStore the UpdateStore usecase for linking association
     */
    public CreateItemServiceImpl(ItemJpaRepository repo, FindStoreService findStore,
                                 UpdateStoreService updateStore) {
        this.repo = repo;
        this.findStore = findStore;
        this.updateStore = updateStore;
    }


    @Override
    public Item create(ItemDto item, UUID storeId) throws DuplicateResourceException, ResourceNotFoundException {
        Store store = findStore.findById(storeId);
        Item createdItem = Item.builder()
                .name(item.getName())
                .price(item.getPrice())
                .store(store)
                .build();

        createdItem = repo.save(createdItem);
        updateStore.addItem(store, createdItem);
        return createdItem;
    }
}
