package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.storeport.IFindStore;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindStore implements IFindStore {
    @Override
    public Store findById(UUID id) throws ResourceNotFoundException {
        return null;
    }
}
