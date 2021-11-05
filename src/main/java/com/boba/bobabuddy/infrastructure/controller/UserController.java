package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.request.CreateUserRequest;
import com.boba.bobabuddy.core.usecase.port.userport.*;
import com.boba.bobabuddy.core.usecase.user.exceptions.UserAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final ICreateUser createUser;
    private final IFindUser findUser;
    private final IRemoveUser removeUser;
    private final IUpdateUser updateUser;

    @Autowired
    public UserController(ICreateUser createUser, IFindUser findUser, ILoginUser loginUser, IRemoveUser removeUser, IUpdateUser updateUser) {
        this.createUser = createUser;
        this.findUser = findUser;
        this.removeUser = removeUser;
        this.updateUser = updateUser;
    }

    @PostMapping(path = "/api/user/")
    public User createUser(@RequestBody CreateUserRequest createUserRequest) throws UserAlreadyExists {
        User user = createUserRequest.createUser();
        if (findUser.userExistanceCheck(user.getEmail())) {
            throw new UserAlreadyExists();
        }
        return createUser.create(createUserRequest.createUser());
    }

    @GetMapping(path = "/api/user/{email}")
    public User findByEmail(@PathVariable String email) throws ResourceNotFoundException {
        return findUser.findByEmail(email);
    }

    @GetMapping(path = "/api/user/", params = "name")
    public List<User> findByName(@RequestParam("name") String name) {
        return findUser.findByName(name);
    }

    @GetMapping(path = "/api/user/")
    public List<User> findAll() {
        return findUser.findAll();
    }

    @DeleteMapping(path = "/api/user/{email}")
    public User removeUserByEmail(@PathVariable String email) throws ResourceNotFoundException {
        return removeUser.removeByEmail(email);
    }

    @PutMapping(path = "/api/user/{email}")
    public User updateUser(@PathVariable String email, @RequestBody User userPatch)
            throws DifferentResourceException, ResourceNotFoundException {
        return updateUser.updateUser(findByEmail(email), userPatch);

    }

//    @GetMapping(path = "/api/user/{email}")
//    public boolean loginUser(@RequestBody LoginUserRequest loginUserRequest, @PathVariable String email) throws ResourceNotFoundException {
//        User user = findUser.findByEmail(email);
//        return loginUser.logIn(user, loginUserRequest.getPassword());
//    }
}
