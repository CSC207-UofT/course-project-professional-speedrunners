package com.boba.bobabuddy.core.usecase.item;



import com.boba.bobabuddy.core.usecase.port.IRequest.IItemIn.IRemoveItem;
import com.boba.bobabuddy.core.usecase.port.IResponse.PresenterInterface;
import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * This class handle the usecase of removing items in the system.
 * It implements the IRemoveItem interface which defines what operations are supported by the usecase object
 * from a controller's perspective.
 */
@Service
@Transactional
public class RemoveItem implements IRemoveItem {

    private final ItemJpaRepository repo;
    private final PresenterInterface presenter;

    /**
     * Initalize RemoveItem usecase by injecting dependencies
     * @param repo database object for handling item data
     * @param presenter a presenter object responsible for translating data from usecase readable format into view
     *                  object readable format (view model).
     */
    @Autowired
    public RemoveItem(final ItemJpaRepository repo, PresenterInterface presenter) {
        this.repo = repo;
        this.presenter = presenter;
    }

    //usecase interactors
    /**
     * a usecase interactor.
     * the method removes an item from database that has the matching itemId
     */
    @Override
    public Item execute(UUID id) {
        return repo.removeById(id);
    }

}
