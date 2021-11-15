package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.user.port.IUpdateUser;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;
import com.boba.bobabuddy.infrastructure.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class UpdateUser implements IUpdateUser {

    private final UserJpaRepository repo;

    @Autowired
    public UpdateUser(UserJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public User updateUser(User userToUpdate, UserDto newUser) throws DifferentResourceException {

        if (Objects.equals(userToUpdate.getEmail(), newUser.getEmail())) {
            userToUpdate.setName(newUser.getName());
            userToUpdate.setPassword(newUser.getPassword());
            return repo.save(userToUpdate);

        }
        throw new DifferentResourceException("Not the same user", new Exception());
    }

    @Override
    public User addRating(User userToUpdate, Rating rating) throws DuplicateResourceException {
        if (userToUpdate.addRating(rating)) {
            return repo.save(userToUpdate);
        }
        throw new DuplicateResourceException("add Rating failed");
    }


}
