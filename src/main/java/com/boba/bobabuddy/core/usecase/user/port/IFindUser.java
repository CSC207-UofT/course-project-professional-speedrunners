package com.boba.bobabuddy.core.usecase.user.port;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;
/**
 * Usecase Input Boundary
 */
public interface IFindUser {

    User findById(UUID id) throws ResourceNotFoundException;

    User findByEmail(String email) throws ResourceNotFoundException;

    List<User> findAll();

    List<User> findByName(String name);

    boolean userExistenceCheck(String email);

    User findByRating(UUID id) throws ResourceNotFoundException;
}
