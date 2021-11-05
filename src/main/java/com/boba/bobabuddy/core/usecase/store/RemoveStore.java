package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.storeport.IRemoveStore;
import com.boba.bobabuddy.core.usecase.store.exceptions.StoreNotFoundException;
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

public class RemoveStore implements IRemoveStore {

    private final StoreJpaRepository repo;

    /**
     * Initialize RemoveStore usecase by injecting dependencies
     *
     * @param repo database object for handling store data
     */

    @Autowired
    public RemoveStore(final StoreJpaRepository repo) {
        this.repo = repo;
    }

    /**
     * Removes a store from database that has the matching storeId.
     *
     * @param id id of the store.
     * @return Store that was removed from the database.
     * @throws StoreNotFoundException thrown when store was not found
     */
    public Store removeById(UUID id) throws ResourceNotFoundException {
        return repo.removeById(id).orElseThrow(() -> new ResourceNotFoundException("No such store", new Exception()));

    }
}
