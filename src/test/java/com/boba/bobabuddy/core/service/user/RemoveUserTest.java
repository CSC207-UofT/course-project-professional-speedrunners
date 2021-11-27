package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.domain.builder.UserBuilder;
import com.boba.bobabuddy.core.service.user.impl.RemoveUserServiceImpl;
import com.boba.bobabuddy.core.data.dao.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoveUserTest {
    @Mock
    private UserJpaRepository repo;

    @Mock
    private FindUserService findUser;

    @InjectMocks
    private RemoveUserServiceImpl removeUser;

    @Test
    void testRemove() {
        String name = "name";
        String email = "name@gmail.com";
        String password = "password";
        User user = new UserBuilder().setName(name).setEmail(email).setPassword(password).createUser();

        when(findUser.findByEmail(email)).thenReturn(user);

        removeUser.removeByEmail(email);
        verify(repo, Mockito.times(1)).delete(user);
    }
}
