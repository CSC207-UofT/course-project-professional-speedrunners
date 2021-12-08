package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.data.dto.UserDto;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.service.user.CreateUserService;
import com.boba.bobabuddy.core.service.user.FindUserService;
import com.boba.bobabuddy.core.service.user.RemoveUserService;
import com.boba.bobabuddy.core.service.user.UpdateUserService;
import com.boba.bobabuddy.framework.util.DtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class,
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class) },
        excludeAutoConfiguration = { SecurityAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @MockBean
    private CreateUserService createUser;

    @MockBean
    private FindUserService findUser;

    @MockBean
    private RemoveUserService removeUser;

    @MockBean
    private UpdateUserService updateUser;

    @MockBean
    private DtoConverter<User, UserDto> converter;

    @Mock
    private Rating rating;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private User user1, user2, user3;
    private UserDto userDto1, userDto2, userDto3;

    @BeforeEach
    void setup(){
        String email1 = "name@gmail.com";
        String email2 = "name@hotmail.com";
        String email3 = "name@mail.utoronto.ca";
        String password = "password";
        String name1 = "name1";
        String name2 = "name2";
        user1 = User.builder().name(name2).email(email1).password(password).build();
        userDto1 = UserDto.builder().name(name2).email(email1).password(password).build();
        user2 = User.builder().name(name1).email(email2).password(password).build();
        userDto2 = UserDto.builder().name(name1).email(email2).password(password).build();
        user3 = User.builder().name(name2).email(email3).password(password).build();
        userDto3 = UserDto.builder().name(name2).email(email3).password(password).build();
    }

    @AfterEach
    void teardown(){
        user1 = null;
        user2 = null;
        user3 = null;
    }

    @Test
    void testCreateUser() throws Exception{
        when(createUser.create(ArgumentMatchers.isA(UserDto.class))).thenReturn(user1);
        when(converter.convertToDto(any())).thenReturn(userDto1);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto1)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))//Test out APPLICATION_JSON
                .andExpect(jsonPath("$.email", is(user1.getEmail())))
                .andExpect(jsonPath("$.name", is(user1.getName())))
                .andExpect(jsonPath("$.password", is(user1.getPassword())));//Add something to check that ratings is empty
    }

    @Test
    void testFindByEmail() throws Exception {
        when(findUser.findByEmail(user1.getEmail())).thenReturn(user1);
        when(converter.convertToDto(any())).thenReturn(userDto1);


        mockMvc.perform(get("/users/{email}", user1.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))//Test out APPLICATION_JSON

                .andExpect(jsonPath("$.email", is(user1.getEmail())))
                .andExpect(jsonPath("$.name", is(user1.getName())))
                .andExpect(jsonPath("$.password", is(user1.getPassword())));//Add something to check that ratings is same
    }

    @Test
    void testFindByName() throws Exception {
        List<User> userNameList = Arrays.asList(user1, user3);
        when(findUser.findByName(user1.getName())).thenReturn(userNameList);
        when(converter.convertToDtoList(any())).thenReturn(Arrays.asList(userDto1, userDto3));


        mockMvc.perform(get("/admin/users/?name={name}", user1.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))//Test out APPLICATION_JSON

                .andExpect(jsonPath("$[0].email", is(user1.getEmail())))
                .andExpect(jsonPath("$[0].name", is(user1.getName())))
                .andExpect(jsonPath("$[0].password", is(user1.getPassword())))
                .andExpect(jsonPath("$[1].email", is(user3.getEmail())))
                .andExpect(jsonPath("$[1].name", is(user3.getName())))
                .andExpect(jsonPath("$[1].password", is(user3.getPassword())));
    }

    @Test
    void testFindAll() throws Exception{
        List<User> userList = Arrays.asList(user1, user2, user3);
        List<UserDto> userDtos = Arrays.asList(userDto1, userDto2, userDto3);
        when(findUser.findAll()).thenReturn(userList);
        when(converter.convertToDtoList(any())).thenReturn(userDtos);

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))//Test out APPLICATION_JSON

                .andExpect(jsonPath("$[0].email", is(user1.getEmail())))
                .andExpect(jsonPath("$[0].name", is(user1.getName())))
                .andExpect(jsonPath("$[0].password", is(user1.getPassword())))
                .andExpect(jsonPath("$[1].email", is(user2.getEmail())))
                .andExpect(jsonPath("$[1].name", is(user2.getName())))
                .andExpect(jsonPath("$[1].password", is(user2.getPassword())))
                .andExpect(jsonPath("$[2].email", is(user3.getEmail())))
                .andExpect(jsonPath("$[2].name", is(user3.getName())))
                .andExpect(jsonPath("$[2].password", is(user3.getPassword())));
    }

    @Test
    void testRemoveUserByEmail(){

    }

    @Test
    void testUpdateUser(){

    }
}
