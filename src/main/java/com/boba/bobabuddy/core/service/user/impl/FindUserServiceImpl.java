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


    @Override
    public User findByEmail(String email) throws ResourceNotFoundException {
        return repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }


    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public List<User> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public boolean userExistenceCheck(String email) {
        return repo.findByEmail(email).isPresent();
    }

    @Override
    public User findByRating(UUID id) throws ResourceNotFoundException {
        return repo.findByRatings_id(id).orElseThrow(() -> new ResourceNotFoundException("No user with this rating entry exist"));
    }
}
