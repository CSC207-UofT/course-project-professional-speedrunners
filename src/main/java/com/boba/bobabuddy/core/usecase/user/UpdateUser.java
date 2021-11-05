package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.port.userport.IUpdateUser;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;
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
    public User updateUser(User userToUpdate, User newUser) throws DifferentResourceException {

        if (Objects.equals(userToUpdate, newUser)) {
            userToUpdate.setName(newUser.getName());
            userToUpdate.setEmail(newUser.getEmail());
            userToUpdate.setPassword(newUser.getPassword());
            userToUpdate.setRatings(newUser.getRatings());
            return repo.save(userToUpdate);

        }
        throw new DifferentResourceException("Not the same user", new Exception());
    }

    @Override
    public User addRating(User userToUpdate, RatingPoint ratingPoint) throws DuplicateResourceException {
        if (userToUpdate.addRating(ratingPoint)) {
            return repo.save(userToUpdate);
        }
        throw new DuplicateResourceException("add Rating failed");
    }


}
