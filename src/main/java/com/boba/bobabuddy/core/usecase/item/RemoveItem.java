package com.boba.bobabuddy.core.usecase.item;


import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.port.IResponse.PresenterInterface;
import com.boba.bobabuddy.core.usecase.port.itemport.IRemoveItem;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class handle the usecase of removing items in the system.
 * It implements the IRemoveItem interface which defines what operations are supported by the usecase object
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
public class RemoveItem implements IRemoveItem {

    private final ItemJpaRepository repo;
    private final PresenterInterface presenter; //depreciated

    /**
     * Initialize RemoveItem usecase by injecting dependencies
     *
     * @param repo      database object for handling item data
     * @param presenter a presenter object responsible for translating data from usecase readable format into view
     *                  object readable format (view model).
     */
    // Spring annotation that instruct springboot to attempt to automatically inject dependencies as needed.
    @Autowired
    public RemoveItem(final ItemJpaRepository repo, PresenterInterface presenter) {
        this.repo = repo;
        this.presenter = presenter;
    }

    /***
     * removes an item from database that has the matching itemId.
     * @param id id of the Item
     * @return Item that was removed from the database
     */
    @Override
    public Item removeById(UUID id) {
        return repo.removeById(id);
    }

    //TODO: add removeByStore
}
