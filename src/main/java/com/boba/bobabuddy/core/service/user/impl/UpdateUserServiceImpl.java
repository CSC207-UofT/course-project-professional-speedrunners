package com.boba.bobabuddy.core.service.user.impl;

import com.boba.bobabuddy.core.data.dao.UserJpaRepository;
import com.boba.bobabuddy.core.data.dto.UserDto;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.service.user.FindUserService;
import com.boba.bobabuddy.core.service.user.UpdateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * This class handle the usecase of updating user info to the database
 */
@Service("UpdateUserService")
@Transactional
public class UpdateUserServiceImpl implements UpdateUserService {

    private final UserJpaRepository repo;
    private final FindUserService findUserService;

    @Autowired
    public UpdateUserServiceImpl(UserJpaRepository repo, FindUserService findUserService) {
        this.repo = repo;
        this.findUserService = findUserService;
    }


    @Override
    public User updateUser(String email, UserDto newUser) throws DifferentResourceException {
        User userToUpdate = findUserService.findByEmail(email);
        User user = User.builder()
                .id(userToUpdate.getId())
                .ratings(userToUpdate.getRatings())
                .roles(userToUpdate.getRoles())
                .email(Optional.ofNullable(newUser.getEmail()).orElse(userToUpdate.getEmail()))
                .imageUrl(Optional.ofNullable(newUser.getImageUrl()).orElse(userToUpdate.getImageUrl()))
                .name(Optional.ofNullable(newUser.getName()).orElse(userToUpdate.getName()))
                .password(Optional.ofNullable(newUser.getPassword()).orElse(userToUpdate.getPassword()))
                .build();
        return repo.save(user);
    }


    @Override
    public User addRating(User userToUpdate, Rating rating) throws DuplicateResourceException {
        if (userToUpdate.addRating(rating)) {
            return repo.save(userToUpdate);
        }
        throw new DuplicateResourceException("add Rating failed");
    }

    @Override
    public User updateUserImage(UUID userId, String imageUrl) {
        User userToUpdate = findUserService.findById(userId);
        userToUpdate.setImageUrl(imageUrl);
        return repo.save(userToUpdate);
    }


}
