package com.boba.bobabuddy.core.usecase.port.itemport;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentItemException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.infrastructure.controller.port.UpdateItemRequest;
import org.springframework.stereotype.Component;

@Component
public interface IUpdateItem {
    Item updateItem(Item itemToUpdate, Item newItem) throws ResourceNotFoundException, DifferentItemException;
}
