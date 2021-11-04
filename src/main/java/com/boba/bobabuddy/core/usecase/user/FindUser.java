package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.port.userport.IFindUser;
import com.boba.bobabuddy.core.usecase.user.exceptions.UserNotFoundException;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FindUser implements IFindUser {

    private final UserJpaRepository repo;

    public FindUser(UserJpaRepository repo){
        this.repo = repo;
    }

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> user = Optional.ofNullable(repo.findByEmail(email));
        if (user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException();
    }

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public List<User> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public boolean userExistanceCheck(String email) {
        Optional<User> user = Optional.ofNullable(repo.findByEmail(email));
        return user.isPresent();
    }
}
