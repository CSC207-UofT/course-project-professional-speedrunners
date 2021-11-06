package com.boba.bobabuddy.core.usecase.user.port;

import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

public interface IRemoveUser {
    void removeByEmail(String email) throws ResourceNotFoundException;
}
