package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.store.port.ICreateStore;
import com.boba.bobabuddy.infrastructure.dao.StoreJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class handle the usecase of creating store and adding it into the system.
 */

@Service
@Transactional
public class CreateStore implements ICreateStore {
    private final StoreJpaRepository repo;

    /**
     * Initialize the Create Store usecase by injecting it with required dependencies.
     *
     * @param repo DAO for handling store entity
     */
    public CreateStore(StoreJpaRepository repo) {
        this.repo = repo;
    }

    /***
     * Persist a new store entity to the database
     * @param store Store to be persisted in the database.
     * @return the Store object that was persisted in the database.
     */
    @Override
    public Store create(Store store) {
        return repo.save(store);
    }

}
