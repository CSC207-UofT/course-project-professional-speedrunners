package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.domain.builder.UserBuilder;
import com.boba.bobabuddy.core.data.dao.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LoginUserTest {
    @Mock
    private UserJpaRepository repo;

    @InjectMocks
    private LoginUser loginUser;

    @Test
    void setup() {
        String email = "name@gmail.com";
        String name = "name";
        String password = "password";
        User user = new UserBuilder().setName(name).setEmail(email).setPassword(password).createUser();
        assertTrue(loginUser.logIn(user, "password"));
        assertFalse(loginUser.logIn(user, "notthepassword"));
    }
}
