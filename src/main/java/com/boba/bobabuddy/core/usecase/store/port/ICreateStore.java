package com.boba.bobabuddy.core.usecase.store.port;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.infrastructure.dto.StoreDto;
import org.springframework.stereotype.Component;
/**
 * Usecase Input Boundary
 */
@Component
public interface ICreateStore {
    Store create(StoreDto store);
}
