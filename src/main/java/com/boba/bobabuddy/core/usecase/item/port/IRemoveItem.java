package com.boba.bobabuddy.core.usecase.item.port;

import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;
/**
 * Usecase Input Boundary
 */
@Component
public interface IRemoveItem {
    void removeById(UUID id) throws ResourceNotFoundException;
}
