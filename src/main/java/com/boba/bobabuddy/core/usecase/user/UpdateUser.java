package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.port.userport.IUpdateUser;
import com.boba.bobabuddy.core.usecase.user.exceptions.UserNotFoundException;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;

import java.util.Optional;

public class UpdateUser implements IUpdateUser {

    private final UserJpaRepository repo;

    public UpdateUser(UserJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public User updateUser(String email, User newUser) throws UserNotFoundException {

        Optional<User> user = Optional.ofNullable(repo.findByEmail(email));

        if(user.isPresent()){
            User updatedUser = user.get();
            updatedUser.setName(newUser.getName());
            updatedUser.setEmail(newUser.getEmail());
            updatedUser.setPassword(newUser.getPassword());
            repo.save(updatedUser);
            return updatedUser;
        }
        throw new UserNotFoundException();
    }
}
