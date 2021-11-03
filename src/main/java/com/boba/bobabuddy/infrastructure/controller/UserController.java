package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.port.request.*;
import com.boba.bobabuddy.core.usecase.port.userport.*;
import com.boba.bobabuddy.core.usecase.user.exceptions.UserNotFoundException;

import java.util.List;

public class UserController {

    private final ICreateUser createUser;
    private final IFindUser findUser;
    private final ILoginUser loginUser;
    private final IRemoveUser removeUser;
    private final IUpdateUser updateUser;

    public UserController(ICreateUser createUser, IFindUser findUser, ILoginUser loginUser, IRemoveUser removeUser, IUpdateUser updateUser){
        this.createUser = createUser;
        this.findUser = findUser;
        this.loginUser = loginUser;
        this.removeUser = removeUser;
        this.updateUser = updateUser;
    }

    public User createUser(CreateUserRequest createUserRequest){
        return createUser.create(createUserRequest.createUser());
    }

    public User findByEmail(FindByEmailRequest findByEmailRequest) throws UserNotFoundException {
        return findUser.findByEmail(findByEmailRequest.getEmail());
    }

    public List<User> findByName(FindByNameRequest findByNameRequest){
        return findUser.findByName(findByNameRequest.getName());
    }

    public List<User> findAll(){
        return findUser.findAll();
    }

    public User removeUserByEmail(RemoveByEmailRequest removeByEmailRequest){
        return removeUser.removeByEmail(removeByEmailRequest.getEmail());
    }

    public User updateUser(UpdateUserRequest updateUserRequest){

    }
}
