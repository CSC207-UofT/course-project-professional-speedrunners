package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.user.port.*;
import com.boba.bobabuddy.infrastructure.assembler.UserResourceAssembler;
import com.boba.bobabuddy.infrastructure.dto.converter.DtoConverter;
import com.boba.bobabuddy.infrastructure.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class UserController {

    private final ICreateUser createUser;
    private final IFindUser findUser;
    private final IRemoveUser removeUser;
    private final IUpdateUser updateUser;
    private final UserResourceAssembler assembler;
    private final DtoConverter<User, UserDto> dtoConverter;




    @Autowired
    public UserController(ICreateUser createUser, IFindUser findUser, IRemoveUser removeUser, IUpdateUser updateUser, ModelMapper mapper, UserResourceAssembler assembler) {
        this.createUser = createUser;
        this.findUser = findUser;
        this.removeUser = removeUser;
        this.updateUser = updateUser;
        this.assembler = assembler;
        this.dtoConverter = new DtoConverter<>(mapper, User.class, UserDto.class);
    }

    @PostMapping(path = "/users")
    public ResponseEntity<EntityModel<UserDto>> createUser(@RequestBody UserDto createUserRequest) {
        UserDto userToPresent = dtoConverter.convertToDto(createUser.create(dtoConverter.convertToEntity(createUserRequest)));
        return ResponseEntity.created(linkTo(methodOn(UserController.class).findByEmail(userToPresent.getEmail())).toUri()).body(assembler.toModel(userToPresent));
    }

    @GetMapping(path = "/users/{email}")
    public ResponseEntity<EntityModel<UserDto>> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(assembler.toModel(dtoConverter.convertToDto(findUser.findByEmail(email))));

    }

    @GetMapping(path = "/users", params = "name")
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findUser.findByName(name))));
    }

    @GetMapping(path = "/users")
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> findAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findUser.findAll())));
    }

    @DeleteMapping(path = "/users/{email}")
    public ResponseEntity<?> removeUserByEmail(@PathVariable String email) {
        removeUser.removeByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/users/{email}")
    public ResponseEntity<EntityModel<UserDto>> updateUser(@PathVariable String email, @RequestBody UserDto userPatch) {
        return ResponseEntity.ok(assembler.toModel(dtoConverter.convertToDto(updateUser.updateUser(findUser.findByEmail(email), userPatch))));
    }

//    public ResponseEntity<EntityModel<User>> findByRating(UUID id) {
//       try {
//           return ResponseEntity.ok(assembler.toModel(findUser.findByRating(id)));
//       } catch (ResourceNotFoundException e){
//           throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
//       }
//    }

//    @GetMapping(path = "/user/{email}")
//    public boolean loginUser(@RequestBody LoginUserRequest loginUserRequest, @PathVariable String email) throws ResourceNotFoundException {
//        User user = findUser.findByEmail(email);
//        return loginUser.logIn(user, loginUserRequest.getPassword());
//    }
}
