package com.boba.bobabuddy.core.usecase.port.userport;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

public interface IRemoveUser {
    User removeByEmail(String email) throws ResourceNotFoundException;
}
