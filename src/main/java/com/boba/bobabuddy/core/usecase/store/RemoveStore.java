package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.port.IResponse.PresenterInterface;
import com.boba.bobabuddy.core.usecase.port.storeport.IRemoveStore;
import com.boba.bobabuddy.infrastructure.database.StoreJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class handle the usecase fo removing stores in the system.
 * It implements the IRemoveStore interface which defines what operations are supported by the usecase object
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

public class RemoveStore implements IRemoveStore{

    private final StoreJpaRepository repo;
    private final PresenterInterface presenter; //depreciated

    /**
     * Initialize RemoveStore usecase by injecting dependencies
     *
     * @param repo database object for handling store data
     * @param presenter a presenter object responsible for translating data from usecase readable format into view
     *                  object readable format (view model).
     */

    @Autowired
    public RemoveStore(final StoreJpaRepository repo, PresenterInterface presenter){
        this.repo = repo;
        this.presenter = presenter;
    }

    /**
     * Removes an store from database that has the matching storeId.
     * @param id id of the store.
     * @return Store that was removed from the database.
     */
    @Override
    public Store removeStoreById(UUID id){
        return repo.removeStoreById(id);
    }
}
