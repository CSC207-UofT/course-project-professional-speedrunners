package com.boba.bobabuddy.core.usecase.store.port;

import com.boba.bobabuddy.core.entity.Store;
import org.springframework.stereotype.Component;

@Component
public interface ICreateStore {
    Store create(Store store);
}
