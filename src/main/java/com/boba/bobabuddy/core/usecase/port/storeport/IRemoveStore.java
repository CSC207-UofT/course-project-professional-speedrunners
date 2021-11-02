package com.boba.bobabuddy.core.usecase.port.storeport;

import com.boba.bobabuddy.core.entity.Store;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface IRemoveStore {
    Store removeStoreById(UUID id);
}
