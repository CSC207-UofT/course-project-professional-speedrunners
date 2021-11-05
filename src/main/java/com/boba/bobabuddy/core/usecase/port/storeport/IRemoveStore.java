package com.boba.bobabuddy.core.usecase.port.storeport;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.store.exceptions.StoreNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface IRemoveStore {
    Store removeById(UUID id) throws ResourceNotFoundException;
}
