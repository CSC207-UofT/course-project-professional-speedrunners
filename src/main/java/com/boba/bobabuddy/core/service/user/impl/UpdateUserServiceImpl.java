package com.boba.bobabuddy.core.service.user.impl;

import com.boba.bobabuddy.core.data.dao.UserJpaRepository;
import com.boba.bobabuddy.core.data.dto.UserDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.service.firebaseImage.IImageService;
import com.boba.bobabuddy.core.service.user.FindUserService;
import com.boba.bobabuddy.core.service.user.UpdateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * This class handle the usecase of updating user info to the database
 */
@Service("UpdateUserService")
@Transactional
public class UpdateUserServiceImpl implements UpdateUserService {

    private final UserJpaRepository repo;
    private final FindUserService findUserService;
    private final IImageService imageService;

    @Autowired
    public UpdateUserServiceImpl(UserJpaRepository repo, FindUserService findUserService,
                                 IImageService imageService) {
        this.repo = repo;
        this.findUserService = findUserService;
        this.imageService = imageService;
    }

    /**
     * Update user fields and save it to the database
     *
     * @param userToUpdate old user data
     * @param newUser      user DTO containing new data and matching id
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
        throw new DifferentResourceException("Not the same user");
    }

    /**
     * Internal usecase for creating of ratings
     * used for adding rating pointer to the user object during rating creation
     *
     * @param userToUpdate user to add rating to
     * @param rating       the rating
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

    @Override
    public User updateUserImage(UUID userId, String imageUrl) throws IOException {
        User userToUpdate = findUserService.findById(userId);

        String fileName = imageService.save(imageUrl, StringUtils.getFilename(imageUrl));
        String fbImageUrl = imageService.getImageUrl(fileName);

        userToUpdate.setImageUrl(fbImageUrl);

        return repo.save(userToUpdate);
    }


}
