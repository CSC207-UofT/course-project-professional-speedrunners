package com.boba.bobabuddy.core.usecase.port.userport;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IFindUser {

    User findByEmail(String email) throws ResourceNotFoundException;

    List<User> findAll();

    List<User> findByName(String name);

    boolean userExistanceCheck(String email);
}
