package com.boba.bobabuddy.core.usecase.item;


import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.port.IRequest.IItemIn.ICreateItem;
import com.boba.bobabuddy.core.usecase.port.IResponse.PresenterInterface;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * This class handle the usecase of creating item and adding it into the system.
 * It implements the ICreateItem interface which defines what operations are supported by the usecase object
 * from a controller's perspective.
 */
@Service
@Transactional
public class CreateItem implements ICreateItem{
    private final ItemJpaRepository repo; // port for database dependency injection.
    private final PresenterInterface presenter; // port for injecting presenter implementations.

    /**
     * Initialize the Create Item usecase by injecting it with required dependencies.
     * @param repo a database object for handling item data
     * @param presenter a presenter object responsible for translating data from usecase readable format into view
     *                  object readable format (view model).
     */
    @Autowired
    public CreateItem(ItemJpaRepository repo, PresenterInterface presenter) {
        this.repo = repo;
        this.presenter = presenter;
    }

    /**
     * a usecase interactor.
     * the method responsible for performing item creation.
     * note that usecase methods passes the response directly to the presenter within the method instead of
     * generating a return type.
     */

    @Override
    public Item create(Item item) {
        return repo.save(item);
    }
}
