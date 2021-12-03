package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.user.port.ICreateUser;
import com.boba.bobabuddy.core.usecase.user.port.IFindUser;
import com.boba.bobabuddy.infrastructure.dao.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***
 * This class handles the persisting of a new user to the database.
 */
@Service
@Transactional
public class CreateUser implements ICreateUser {
    private final UserJpaRepository repo;
    private final IFindUser findUser;

    /***
     * Constructor for injecting dependencies
     * @param repo DAO for interacting with user data
     * @param findUser FindUser usecase for checking duplication
     */
    @Autowired
    public CreateUser(final UserJpaRepository repo, IFindUser findUser) {
        this.repo = repo;
        this.findUser = findUser;
    }

    /***
     * Persist a new user to the database
     * @param user User to be persisted to the database
     * @return the persisted user entity
     * @throws DuplicateResourceException thrown when user with the same email exists in the database
     */
    @Override
    public User create(User user) throws DuplicateResourceException {
        if (findUser.userExistenceCheck(user.getEmail())) {
            throw new DuplicateResourceException("User already exist");
        }
        return repo.save(user);
    }
}
