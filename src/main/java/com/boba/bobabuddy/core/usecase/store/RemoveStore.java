package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.store.port.IFindStore;
import com.boba.bobabuddy.core.usecase.store.port.IRemoveStore;
import com.boba.bobabuddy.infrastructure.database.StoreJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class handle the usecase fo removing stores in the system.
 */

@Service
@Transactional
public class RemoveStore implements IRemoveStore {

    private final StoreJpaRepository repo;
    private final IFindStore findStore;

    /**
     * Initialize RemoveStore usecase by injecting dependencies
     *
     * @param repo      DAO for handling store data
     * @param findStore
     */

    @Autowired
    // used because we want to inject StoreJpaRepository here
    public RemoveStore(final StoreJpaRepository repo, IFindStore findStore) {
        this.repo = repo;
        this.findStore = findStore;
    }

    /**
     * Removes a store from database that has the matching storeId.
     *
     * @param id id of the store.
     * @throws ResourceNotFoundException thrown when store was not found
     */
    public void removeById(UUID id) throws ResourceNotFoundException {
        Store store = findStore.findById(id);
        repo.delete(store);
    }
}
