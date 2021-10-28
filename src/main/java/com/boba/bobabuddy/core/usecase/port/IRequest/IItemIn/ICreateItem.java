package com.boba.bobabuddy.core.usecase.port.IRequest.IItemIn;

import com.boba.bobabuddy.core.entity.Item;
import org.springframework.stereotype.Component;

@Component
public interface ICreateItem {

    Item create(Item item);
}
