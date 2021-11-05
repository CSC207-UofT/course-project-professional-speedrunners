package com.boba.bobabuddy.core.usecase.port.storeport;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public interface IUpdateStore {
    Store updateStore(Store storeToUpdate, Store storePatch) throws DifferentResourceException;

    Store addItem(Store store, Item item) throws DuplicateResourceException;

    Store removeItem(Store store, Item item) throws ResourceNotFoundException;
}
