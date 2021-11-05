package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.port.userport.ICreateUser;
import com.boba.bobabuddy.core.usecase.port.userport.IFindUser;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateUser implements ICreateUser {
    private final UserJpaRepository repo;
    private final IFindUser findUser;

    @Autowired
    public CreateUser(final UserJpaRepository repo, IFindUser findUser) {
        this.repo = repo;
        this.findUser = findUser;
    }

    @Override
    public User create(User user) throws DuplicateResourceException {
        if (findUser.userExistenceCheck(user.getEmail())) {
            throw new DuplicateResourceException("User already exist");
        }
        return repo.save(user);
    }
}
