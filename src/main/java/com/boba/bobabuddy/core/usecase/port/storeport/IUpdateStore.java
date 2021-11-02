package com.boba.bobabuddy.core.usecase.port.storeport;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.store.exceptions.NoSuchStoreException;
import org.springframework.stereotype.Component;

@Component
public interface IUpdateStore {
    Store updateStore(Store store) throws NoSuchStoreException;
}
