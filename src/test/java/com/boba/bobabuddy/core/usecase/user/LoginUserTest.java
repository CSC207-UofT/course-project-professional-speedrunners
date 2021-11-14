package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoginUserTest {
    @Mock
    private UserJpaRepository repo;

    @InjectMocks
    private LoginUser loginUser;

    @Test
    void setup(){
        String email = "name@gmail.com";
        String name = "name";
        String password = "password";
        User user = new User(name, email, password);
        assertTrue(loginUser.logIn(user, "password"));
        assertFalse(loginUser.logIn(user, "notthepassword"));
    }
}
