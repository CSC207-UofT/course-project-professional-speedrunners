package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.domain.builder.UserBuilder;
import com.boba.bobabuddy.core.service.user.impl.CreateUserServiceImpl;
import com.boba.bobabuddy.core.data.dao.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserTest {

    @Mock
    private UserJpaRepository repo;

    @Mock
    private FindUserService findUser;

    @InjectMocks
    private CreateUserServiceImpl createUser;

    @Test
    void testCreate() {
        String name = "name";
        String email = "name@gmail.com";
        String password = "password";
        Set<Rating> ratings = Collections.emptySet();

        User user = new UserBuilder().setName(name).setEmail(email).setPassword(password).createUser();
        when(repo.save(any())).thenReturn(user);

        User returnedUser = createUser.create(user);

        assertNotNull(returnedUser);
        assertEquals(user.toString(), returnedUser.toString());
        assertEquals(ratings, returnedUser.getRatings());
    }
}
