package com.boba.bobabuddy.core.usecase.port.itemport;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.store.exceptions.StoreNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface ICreateItem {

    Item create(Item item, UUID storeId) throws DuplicateResourceException, ResourceNotFoundException;
}
