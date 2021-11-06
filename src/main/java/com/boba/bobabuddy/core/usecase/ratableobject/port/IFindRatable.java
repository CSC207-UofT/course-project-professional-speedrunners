package com.boba.bobabuddy.core.usecase.ratableobject.port;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.UUID;

public interface IFindRatable {
    RatableObject findById(UUID id) throws ResourceNotFoundException;

}
