package com.boba.bobabuddy.core.usecase.port.itemport;

import com.boba.bobabuddy.core.entity.Item;
import org.springframework.stereotype.Component;

@Component
public interface ICreateItem {

    Item create(Item item);
}
