package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.user.port.IFindUser;
import com.boba.bobabuddy.core.usecase.user.port.IRemoveUser;
import com.boba.bobabuddy.infrastructure.dao.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class handle the usecase of removing users from the database.
 */
@Service
@Transactional
public class RemoveUser implements IRemoveUser {

    private final UserJpaRepository repo;
    private final IFindUser findUser;

    /***
     * Constructor for injecting relevant dependencies
     * @param repo DAO for handling user data
     * @param findUser FindUser usecase to find the user to be removed
     */
    public RemoveUser(UserJpaRepository repo, IFindUser findUser) {
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
