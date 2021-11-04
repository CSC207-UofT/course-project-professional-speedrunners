package com.boba.bobabuddy.core.usecase.port.storeport;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface IFindStore {
    Store findById(UUID id) throws ResourceNotFoundException;
}
