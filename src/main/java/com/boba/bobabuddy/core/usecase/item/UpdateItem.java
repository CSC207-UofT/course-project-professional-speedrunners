package com.boba.bobabuddy.core.usecase.item;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.item.exceptions.NoSuchItemException;
import com.boba.bobabuddy.core.usecase.port.IRequest.IItemIn.IUpdateItem;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * This class handle the usecase of updating items in the system.
 * It implements the IFindItem interface which defines what operations are supported by the usecase object
 * from a controller's perspective.
 */

// Not used as of phase 0
@Service
@Transactional
public class UpdateItem implements IUpdateItem {
    private final ItemJpaRepository repo;

    @Autowired
    public UpdateItem(final ItemJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Item updateItem(Item item) throws NoSuchItemException{
        if (repo.existsById(item.getId())) return repo.save(item);
        throw new NoSuchItemException("No such item", new Exception());
    }


}
