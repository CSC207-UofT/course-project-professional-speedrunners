package com.boba.bobabuddy.core.usecase.port.itemport;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.item.exceptions.NoSuchItemException;
import org.springframework.stereotype.Component;

@Component
public interface IUpdateItem {
    Item updateItem(Item item) throws NoSuchItemException;
}
