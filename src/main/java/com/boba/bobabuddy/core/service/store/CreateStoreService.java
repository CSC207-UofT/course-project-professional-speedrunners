package com.boba.bobabuddy.core.service.store;

import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.boba.bobabuddy.core.domain.Store;

/**
 * Usecase Input Boundary
 */
public interface CreateStoreService {
    /**
     * Persist a new store entity to the database
     *
     * @param store Store to be persisted in the database.
     * @return the Store object that was persisted in the database.
     */
    Store create(StoreDto store);
}
