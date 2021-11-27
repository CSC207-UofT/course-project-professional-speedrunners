package com.boba.bobabuddy.core.service.item.impl;

import com.boba.bobabuddy.core.data.dao.ItemJpaRepository;
import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.service.item.FindItemService;
import com.boba.bobabuddy.core.service.item.UpdateItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class handle the usecase of updating items in the system.
 */
@Service
@Transactional
public class UpdateItemServiceImpl implements UpdateItemService {
    private final ItemJpaRepository repo;
    private final FindItemService findItemService;

    /***
     * Construct the usecase class
     * @param repo the repository that hosts the Item entity.
     * @param findItemService Find item service to look for the item to update
     */
    public UpdateItemServiceImpl(final ItemJpaRepository repo, FindItemService findItemService) {
        this.repo = repo;
        this.findItemService = findItemService;
    }


    @Override
    public Item updateItem(UUID itemId, ItemDto itemPatch) throws DifferentResourceException {
        Item itemToUpdate = findItemService.findById(itemId);

        itemToUpdate.setName(itemPatch.getName());
        itemToUpdate.setPrice(itemPatch.getPrice());

        return repo.save(itemToUpdate);
    }
}
