package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.port.request.*;
import com.boba.bobabuddy.core.usecase.port.userport.*;
import com.boba.bobabuddy.core.usecase.user.exceptions.UserAlreadyExists;
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

    public User createUser(CreateUserRequest createUserRequest) throws UserAlreadyExists {
        User user = createUserRequest.createUser();
        if (findUser.userExistanceCheck(user.getEmail())){
            throw new UserAlreadyExists();
        }
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

    public User updateUserEmail(UpdateUserRequest updateUserRequest) throws UserNotFoundException{
        User updatedUser = updateUserRequest.updateUser();
        if (findUser.userExistanceCheck(updatedUser.getEmail())){
            return updateUser.updateUser(updatedUser.getEmail(), updatedUser);
        }
        else{
            throw new UserNotFoundException();
        }
    }

    public boolean loginUser(LoginUserRequest loginUserRequest) throws UserNotFoundException {
        User user = findUser.findByEmail(loginUserRequest.getEmail());
        return loginUser.logIn(user, loginUserRequest.getPassword());
    }
}
