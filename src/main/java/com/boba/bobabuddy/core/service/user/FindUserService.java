package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface FindUserService {

    User findById(UUID id) throws ResourceNotFoundException;

    User findByEmail(String email) throws ResourceNotFoundException;

    List<User> findAll();

    List<User> findByName(String name);

    boolean userExistenceCheck(String email);

    User findByRating(UUID id) throws ResourceNotFoundException;
}
