package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.port.userport.IRemoveUser;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;

public class RemoveUser implements IRemoveUser {

    private final UserJpaRepository repo;

    public RemoveUser(UserJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public User removeByEmail(String email) {
        return repo.removeByEmail(email);
    }
}
