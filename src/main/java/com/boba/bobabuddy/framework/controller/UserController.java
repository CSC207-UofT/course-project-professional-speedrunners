package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.data.dto.RoleDto;
import com.boba.bobabuddy.core.data.dto.UserDto;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.service.user.CreateUserService;
import com.boba.bobabuddy.core.service.user.FindUserService;
import com.boba.bobabuddy.core.service.user.RemoveUserService;
import com.boba.bobabuddy.core.service.user.UpdateUserService;
import com.boba.bobabuddy.framework.converter.DtoConverter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * REST controller for User related api calls
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    // Interfaces of User usecases and user DTO converter
    private final CreateUserService createUser;
    private final FindUserService findUser;
    private final RemoveUserService removeUser;
    private final UpdateUserService updateUser;
    private final DtoConverter<User, UserDto> converter;


    /**
     * POST HTTP requests for creating User resource and then saving the User to the database
     *
     * @param createUserRequest Request class that contains data necessary to construct an User entity.
     * @return User resource that was created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/users")
    public UserDto createUser(@RequestBody UserDto createUserRequest) {
        createUserRequest.setRoles(List.of(new RoleDto("ROLE_USER")));
        return converter.convertToDto(createUser.create(createUserRequest));
    }

    /**
     * GET requests for the User resource belonging to an email.
     *
     * @param email the email of the User
     * @return the User resource with matching email
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/users/{email}")
    public UserDto findByEmail(@PathVariable String email) {
        return converter.convertToDto(findUser.findByEmail(email));

    }

    /**
     * GET requests for the User resources belonging to a name.
     *
     * @param name the email of the User
     * @return the list of User resources with names matching the requested name
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/users", params = "name")
    public List<UserDto> findByName(@RequestParam("name") String name) {
        return converter.convertToDtoList(findUser.findByName(name));
    }

    /**
     * GET requests for all User resources in the database
     *
     * @return list of all User resources in the database
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/users")
    public List<UserDto> findAll() {
        return converter.convertToDtoList(findUser.findAll());
    }

    /**
     * DELETE requests to remove User by its email from the database.
     *
     * @param email the email of the User to be removed from the database
     * @return NO_CONTENT http status
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/users/{email}")
    public ResponseEntity<?> removeUserByEmail(@PathVariable String email) {
        removeUser.removeByEmail(email);
        return ResponseEntity.noContent().build();
    }

    /**
     * PUT request to update an existing User
     *
     * @param email     email of the User we want to update.
     * @param userPatch the same User with updated fields.
     * @return the User resource after the modification
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/users/{email}")
    public UserDto updateUser(@PathVariable String email, @RequestBody UserDto userPatch) {
        return converter.convertToDto(updateUser.updateUser(findUser.findByEmail(email), userPatch));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/admin/user/token")
    public String loginUser(@RequestBody UserDto userDto) throws FirebaseAuthException {
        FirebaseAuth admin = FirebaseAuth.getInstance();
        UserRecord user = admin.getUserByEmail(userDto.getEmail());
        return admin.createCustomToken(user.getUid());
    }
}
