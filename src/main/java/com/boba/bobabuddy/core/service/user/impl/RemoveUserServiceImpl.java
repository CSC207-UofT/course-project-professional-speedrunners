package com.boba.bobabuddy.core.service.user.impl;

import com.boba.bobabuddy.core.data.dao.UserJpaRepository;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.user.FindUserService;
import com.boba.bobabuddy.core.service.user.RemoveUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class handle the usecase of removing users from the database.
 */
@Service("RemoveUserService")
@Transactional
public class RemoveUserServiceImpl implements RemoveUserService {

    private final UserJpaRepository repo;
    private final FindUserService findUser;

    /***
     * Constructor for injecting relevant dependencies
     * @param repo DAO for handling user data
     * @param findUser FindUser usecase to find the user to be removed
     */
    public RemoveUserServiceImpl(UserJpaRepository repo, FindUserService findUser) {
        this.repo = repo;
        this.findUser = findUser;
    }

    /***
     * removes a user from database that has the matching email.
     * @param email id of the user
     * @throws ResourceNotFoundException when no user with the associated email was found
     */
    @Override
    public void removeByEmail(String email) throws ResourceNotFoundException {
        User user = findUser.findByEmail(email);
        repo.delete(user);
    }
}
