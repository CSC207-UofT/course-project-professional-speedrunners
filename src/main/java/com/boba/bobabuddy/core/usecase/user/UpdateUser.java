package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.user.port.IUpdateUser;
import com.boba.bobabuddy.infrastructure.dao.UserJpaRepository;
import com.boba.bobabuddy.infrastructure.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * This class handle the usecase of updating user info to the database
 */
@Service
@Transactional
public class UpdateUser implements IUpdateUser {

    private final UserJpaRepository repo;

    @Autowired
    public UpdateUser(UserJpaRepository repo) {
        this.repo = repo;
    }

    /**
     * Update user fields and save it to the database
     * @param userToUpdate old user data
     * @param newUser user DTO containing new data and matching id
     * @return saved user entity
     * @throws DifferentResourceException when userToUpdate and newUser have different email
     */
    @Override
    public User updateUser(User userToUpdate, UserDto newUser) throws DifferentResourceException {

        if (Objects.equals(userToUpdate.getEmail(), newUser.getEmail())) {
            userToUpdate.setName(newUser.getName());
            userToUpdate.setPassword(newUser.getPassword());
            return repo.save(userToUpdate);

        }
        throw new DifferentResourceException("Not the same user", new Exception());
    }

    /**
     * Internal usecase for creating of ratings
     * used for adding rating pointer to the user object during rating creation
     * @param userToUpdate user to add rating to
     * @param rating the rating
     * @return the updated User
     * @throws DuplicateResourceException when same rating already exist in user
     */
    @Override
    public User addRating(User userToUpdate, Rating rating) throws DuplicateResourceException {
        if (userToUpdate.addRating(rating)) {
            return repo.save(userToUpdate);
        }
        throw new DuplicateResourceException("add Rating failed");
    }


}
