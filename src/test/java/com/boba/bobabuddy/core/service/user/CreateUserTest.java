package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.data.dao.RoleJpaRepository;
import com.boba.bobabuddy.core.data.dto.RoleDto;
import com.boba.bobabuddy.core.data.dto.UserDto;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.Role;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.service.user.impl.CreateUserServiceImpl;
import com.boba.bobabuddy.core.data.dao.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserTest {

    @Mock
    private UserJpaRepository repo;

    @Mock
    private RoleJpaRepository roleRepo;

    @Mock
    private FindUserService findUser;

    @InjectMocks
    private CreateUserServiceImpl createUser;

    @Test
    void testCreate() {
        String name = "name";
        String email = "name@gmail.com";
        String password = "password";

        UserDto userDto = UserDto.builder()
                .name(name)
                .email(email)
                .password(password)
                .roles(Stream.of("ROLE_ADMIN").map(e -> RoleDto.builder().name(e).build()).collect(Collectors.toList()))
                .build();
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .roles(Stream.of("ROLE_ADMIN").map(e -> Role.builder().name(e).build()).collect(Collectors.toSet()))
                .build();

        when(findUser.userExistenceCheck(email)).thenReturn(false);
        when(roleRepo.findByName("ROLE_ADMIN")).thenReturn(Role.builder().name("ROLE_ADMIN").build());
        when(repo.save(any())).thenReturn(user);

        User returnedUser = createUser.create(userDto);

        assertNotNull(returnedUser);
        assertEquals(user.toString(), returnedUser.toString());
    }
}
