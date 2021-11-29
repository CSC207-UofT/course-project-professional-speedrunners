package com.boba.bobabuddy.core.service.store.impl;

import com.boba.bobabuddy.core.data.dao.StoreJpaRepository;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.store.FindStoreService;
import com.boba.bobabuddy.core.service.store.RemoveStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This class handle the usecase fo removing stores in the system.
 */

@Service("RemoveStoreService")
@Transactional
public class RemoveStoreServiceImpl implements RemoveStoreService {

    private final StoreJpaRepository repo;
    private final FindStoreService findStore;

    /**
     * Initialize RemoveStore usecase by injecting dependencies
     *
     * @param repo      DAO for handling store data
     * @param findStore FindStore usecase
     */

    @Autowired
    public RemoveStoreServiceImpl(final StoreJpaRepository repo, FindStoreService findStore) {
        this.repo = repo;
        this.findStore = findStore;
    }


    public void removeById(UUID id) throws ResourceNotFoundException {
        Store store = findStore.findById(id);
        repo.delete(store);
    }
}
