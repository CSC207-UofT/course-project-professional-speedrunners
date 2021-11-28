package com.boba.bobabuddy.core.service.user.impl;

import com.boba.bobabuddy.core.data.dao.UserJpaRepository;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.user.FindUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/***
 * This class handles the usecase of query on user entity.
 */
@Service("FindUserService")
@Transactional
public class FindUserServiceImpl implements FindUserService {

    private final UserJpaRepository repo;

    /**
     * Initialize FindUser usecase by injecting dependencies
     *
     * @param repo data access object for handling user data
     */
    public FindUserServiceImpl(UserJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public User findById(UUID id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    /***
     * Find user by its email
     * @param email email of the user
     * @return the user if it was found.
     * @throws ResourceNotFoundException Thrown when no user with matching email exist in the database
     */
    @Override
    public User findByEmail(String email) throws ResourceNotFoundException {
        return repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    /***
     * Find all user that exist in the database
     * @return list of all user in the database, or an empty list if no user exist in the database
     */
    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    /***
     * Find user by its name. Have to be fully matching.
     * @param name name to be matched
     * @return user with matching name. Or an empty list if no such user exist
     */
    @Override
    public List<User> findByName(String name) {
        return repo.findByName(name);
    }

    /***
     * Check if a user with the associated email already exists in the database
     * @param email the email to check
     * @return true if such user exist
     */
    @Override
    public boolean userExistenceCheck(String email) {
        return repo.findByEmail(email).isPresent();
    }

    /***
     * Find user that has a matching rating object
     * @param id id of the Rating entity
     * @return user if such user exist
     * @throws ResourceNotFoundException thrown when no user possessing this rating exist
     */
    @Override
    public User findByRating(UUID id) throws ResourceNotFoundException {
        return repo.findByRatings_id(id).orElseThrow(() -> new ResourceNotFoundException("No user with this rating entry exist"));
    }
}
