package com.boba.bobabuddy.core.usecase.port.userport;

import com.boba.bobabuddy.core.entity.User;

public interface IRemoveUser {
    User removeByEmail(String email);
}
