package com.boba.bobabuddy.core.usecase.store.port;

import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface IRemoveStore {
    void removeById(UUID id) throws ResourceNotFoundException;
}
