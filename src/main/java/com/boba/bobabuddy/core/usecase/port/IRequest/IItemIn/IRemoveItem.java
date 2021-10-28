package com.boba.bobabuddy.core.usecase.port.IRequest.IItemIn;

import com.boba.bobabuddy.core.entity.Item;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component

public interface IRemoveItem {
    Item execute(UUID id);
}
