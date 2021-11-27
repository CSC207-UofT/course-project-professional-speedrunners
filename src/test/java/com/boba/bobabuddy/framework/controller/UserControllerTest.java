package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.domain.builder.UserBuilder;
import com.boba.bobabuddy.core.service.user.CreateUserService;
import com.boba.bobabuddy.core.service.user.FindUserService;
import com.boba.bobabuddy.core.service.user.RemoveUserService;
import com.boba.bobabuddy.core.service.user.UpdateUserService;
import com.boba.bobabuddy.core.data.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private CreateUserService createUser;

    @MockBean
    private FindUserService findUser;

    @MockBean
    private RemoveUserService removeUser;

    @MockBean
    private UpdateUserService updateUser;

    @Mock
    private Rating rating;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private User user1, user2, user3;

    @BeforeEach
    void setup(){
        String email1 = "name@gmail.com";
        String email2 = "name@hotmail.com";
        String email3 = "name@mail.utoronto.ca";
        String password = "password";
        String name1 = "name1";
        String name2 = "name2";
        user1 = new UserBuilder().setName(name2).setEmail(email1).setPassword(password).createUser();
        user2 = new UserBuilder().setName(name1).setEmail(email2).setPassword(password).createUser();
        user3 = new UserBuilder().setName(name2).setEmail(email3).setPassword(password).createUser();
    }

    @AfterEach
    void teardown(){
        user1 = null;
        user2 = null;
        user3 = null;
    }

    @Test
    void testCreateUser() throws Exception{
        UserDto createUserRequest = new UserDto(user1.getName(), user1.getEmail(), user1.getPassword());
        when(createUser.create(ArgumentMatchers.isA(User.class))).thenReturn(user1);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createUserRequest)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))//Test out APPLICATION_JSON
                .andExpect(jsonPath("$.email", is(user1.getEmail())))
                .andExpect(jsonPath("$.name", is(user1.getName())))
                .andExpect(jsonPath("$.password", is(user1.getPassword())));//Add something to check that ratings is empty
    }

    @Test
    void testFindByEmail() throws Exception {
        when(findUser.findByEmail(user1.getEmail())).thenReturn(user1);

        mockMvc.perform(get("/users/{email}", user1.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$.email", is(user1.getEmail())))
                .andExpect(jsonPath("$.name", is(user1.getName())))
                .andExpect(jsonPath("$.password", is(user1.getPassword())));//Add something to check that ratings is same
    }

    @Test
    void testFindByName() throws Exception {
        List<User> userNameList = Arrays.asList(user1, user3);
        when(findUser.findByName(user1.getName())).thenReturn(userNameList);

        mockMvc.perform(get("/users/?name={name}", user1.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.users.[0].email", is(user1.getEmail())))
                .andExpect(jsonPath("$._embedded.users.[0].name", is(user1.getName())))
                .andExpect(jsonPath("$._embedded.users.[0].password", is(user1.getPassword())))
                .andExpect(jsonPath("$._embedded.users.[1].email", is(user3.getEmail())))
                .andExpect(jsonPath("$._embedded.users.[1].name", is(user3.getName())))
                .andExpect(jsonPath("$._embedded.users.[1].password", is(user3.getPassword())));
    }

    @Test
    void testFindAll() throws Exception{
        List<User> userList = Arrays.asList(user1, user2, user3);
        when(findUser.findAll()).thenReturn(userList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.users.[0].email", is(user1.getEmail())))
                .andExpect(jsonPath("$._embedded.users.[0].name", is(user1.getName())))
                .andExpect(jsonPath("$._embedded.users.[0].password", is(user1.getPassword())))
                .andExpect(jsonPath("$._embedded.users.[1].email", is(user2.getEmail())))
                .andExpect(jsonPath("$._embedded.users.[1].name", is(user2.getName())))
                .andExpect(jsonPath("$._embedded.users.[1].password", is(user2.getPassword())))
                .andExpect(jsonPath("$._embedded.users.[2].email", is(user3.getEmail())))
                .andExpect(jsonPath("$._embedded.users.[2].name", is(user3.getName())))
                .andExpect(jsonPath("$._embedded.users.[2].password", is(user3.getPassword())));
    }

    @Test
    void testRemoveUserByEmail(){

    }

    @Test
    void testUpdateUser(){

    }
}
