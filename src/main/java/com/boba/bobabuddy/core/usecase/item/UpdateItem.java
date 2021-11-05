package com.boba.bobabuddy.core.usecase.item;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.port.itemport.IUpdateItem;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * This class handle the usecase of updating items in the system.
 * It implements the IFindItem interface which defines what operations are supported by the usecase object
 * from a controller's perspective.
 */

// Springframework annotations that marks this class as a service component
// Functionally identical to the @Component annotation as far as I'm aware, and it essentially
// registers the class as a component (bean) so that Spring can automatically configure and inject dependencies
// as needed.
@Service
// Indicates that operations performed in this class in Transactional.
// Refers to this link for more info: https://java.christmas/2019/24
@Transactional
public class UpdateItem implements IUpdateItem {
    private final ItemJpaRepository repo;

    /***
     * Construct the usecase class
     * @param repo the repository that hosts the Item entity.
     */
    // Spring annotation that instruct springboot to attempt to automatically inject dependencies as needed.
    @Autowired
    public UpdateItem(final ItemJpaRepository repo) {
        this.repo = repo;
    }

    /***
     * Update an Item by overwriting it.
     * The api user is responsible for sending in an Item representation that was modified.
     * If there exists an Item entity within the database that has the same uuid, data will be overwritten and thus
     * updated.
     * However if no Item with the same uuid exist an exception will be thrown.
     * TODO: properly implement the exception
     * @param itemToUpdate Item to update.
     * @param newItem the overwriting item.
     * @return the updated item.
     * @throws DifferentResourceException thrown when newItem have a different id than the itemToUpdate
     */
    @Override
    public Item updateItem(Item itemToUpdate, Item newItem) throws DifferentResourceException {
        if (Objects.equals(itemToUpdate.getId(), newItem.getId())) {
            repo.save(newItem);
        }
        throw new DifferentResourceException("Not the same item", new Exception());
    }


}
