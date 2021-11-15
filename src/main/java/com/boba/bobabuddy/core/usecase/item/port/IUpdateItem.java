package com.boba.bobabuddy.core.usecase.item.port;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.infrastructure.dto.ItemDto;
import com.boba.bobabuddy.infrastructure.dto.SimpleItemDto;
import org.springframework.stereotype.Component;

@Component
public interface IUpdateItem {
    Item updateItem(Item itemToUpdate, SimpleItemDto newItem) throws DifferentResourceException;
}
