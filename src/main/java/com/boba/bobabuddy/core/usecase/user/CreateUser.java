package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.port.userport.ICreateUser;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateUser implements ICreateUser {
    private final UserJpaRepository repo;

    @Autowired
    public CreateUser(final UserJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public User create(User user) {
        return repo.save(user);
    }
}
