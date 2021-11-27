package com.boba.bobabuddy.core.service.user.impl;

import com.boba.bobabuddy.core.data.dao.RoleJpaRepository;
import com.boba.bobabuddy.core.data.dao.UserJpaRepository;
import com.boba.bobabuddy.core.data.dto.UserDto;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.service.user.CreateUserService;
import com.boba.bobabuddy.core.service.user.FindUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.stream.Collectors;

/***
 * This class handles the persisting of a new user to the database.
 */
@Service
@Transactional
public class CreateUserServiceImpl implements CreateUserService {
    private final UserJpaRepository repo;
    private final RoleJpaRepository roleRepo;
    private final FindUserService findUser;


    /***
     * Constructor for injecting dependencies
     * @param repo DAO for interacting with user data
     * @param roleRepo
     * @param findUser FindUser usecase for checking duplication
     */
    @Autowired
    public CreateUserServiceImpl(final UserJpaRepository repo, RoleJpaRepository roleRepo, FindUserService findUser) {
        this.repo = repo;
        this.roleRepo = roleRepo;
        this.findUser = findUser;
    }

    /***
     * Persist a new user to the database
     * @param user User to be persisted to the database
     * @return the persisted user entity
     * @throws DuplicateResourceException thrown when user with the same email exists in the database
     */
    @Override
    public User create(UserDto user) throws DuplicateResourceException {
        if (findUser.userExistenceCheck(user.getEmail())) {
            throw new DuplicateResourceException("User already exist");
        }
        User newUser = User.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                        .map((s) -> roleRepo.findByName(s.getName()))
                        .collect(Collectors.toCollection(HashSet::new)))
                .build();
        return repo.save(newUser);
    }
}
