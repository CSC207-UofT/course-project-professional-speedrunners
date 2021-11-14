package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.user.port.IFindUser;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;
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
    private IFindUser findUser;

    @InjectMocks
    private RemoveUser removeUser;

    @Test
    void testRemove(){
        String name = "name";
        String email = "name@gmail.com";
        String password = "password";
        User user = new User(name, email, password);

        when(findUser.findByEmail(email)).thenReturn(user);

        removeUser.removeByEmail(email);
        verify(repo, Mockito.times(1)).delete(user);
    }
}
