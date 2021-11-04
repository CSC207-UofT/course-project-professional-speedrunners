package com.boba.bobabuddy.core.usecase.port;

import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.UUID;

public interface IGenericResource<T> {
    T findById(UUID id) throws ResourceNotFoundException;

    T removeById(UUID id) throws ResourceNotFoundException;
}
