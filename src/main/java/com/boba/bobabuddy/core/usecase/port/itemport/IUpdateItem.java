package com.boba.bobabuddy.core.usecase.port.itemport;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import org.springframework.stereotype.Component;

@Component
public interface IUpdateItem {
    Item updateItem(Item itemToUpdate, Item newItem) throws DifferentResourceException;
}
