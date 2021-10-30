package com.boba.bobabuddy.core.usecase.port.itemport;

import com.boba.bobabuddy.core.entity.Item;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface IRemoveItem {
    Item removeById(UUID id);
}
