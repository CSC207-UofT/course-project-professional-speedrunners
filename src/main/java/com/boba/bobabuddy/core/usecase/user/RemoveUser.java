package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.userport.IRemoveUser;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RemoveUser implements IRemoveUser {

    private final UserJpaRepository repo;

    public RemoveUser(UserJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public User removeByEmail(String email) throws ResourceNotFoundException {
        return repo.removeByEmail(email).orElseThrow(() -> new ResourceNotFoundException("No such user"));
    }
}
