package com.boba.bobabuddy.core.service.item.impl;


import com.boba.bobabuddy.core.data.dao.ItemJpaRepository;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.item.FindItemService;
import com.boba.bobabuddy.core.service.item.RemoveItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class handle the usecase of removing items in the system.
 */
@Service
@Transactional
public class RemoveItemServiceImpl implements RemoveItemService {

    private final ItemJpaRepository repo;
    private final FindItemService findItem;


    /**
     * Initialize RemoveItem usecase by injecting dependencies
     *
     * @param repo     DAO for handling item data
     * @param findItem FindItem usecase to find the item to be removed
     */
    public RemoveItemServiceImpl(final ItemJpaRepository repo, FindItemService findItem) {
        this.repo = repo;
        this.findItem = findItem;
    }


    @Override
    public void removeById(UUID id) throws ResourceNotFoundException {
        Item item = findItem.findById(id);
        repo.delete(item);
    }
}
