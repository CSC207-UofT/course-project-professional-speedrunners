package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.user.port.IFindUser;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CreateUserTest {

    @Mock
    private UserJpaRepository repo;

    @Mock
    private IFindUser findUser;

    @InjectMocks
    private CreateUser createUser;

    @Test
    void testCreate(){
        String name = "name";
        String email = "name@gmail.com";
        String password = "password";
        Set<Rating> ratings = Collections.emptySet();

        User user = new User(name, email, password);
        when(repo.save(any())).thenReturn(user);

        User returnedUser = createUser.create(user);

        assertNotNull(returnedUser);
        assertEquals(user.toString(), returnedUser.toString());
        assertEquals(ratings, returnedUser.getRatings());
    }
}
