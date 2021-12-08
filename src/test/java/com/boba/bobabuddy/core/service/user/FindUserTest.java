package com.boba.bobabuddy.core.service.user;

import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.data.dao.UserJpaRepository;
import com.boba.bobabuddy.core.service.user.impl.FindUserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindUserTest {
    @Mock
    private UserJpaRepository repo;
    @Mock
    private Rating rating;

    @InjectMocks
    private FindUserServiceImpl findUser;

    private List<User> userList, userNameList;
    private User user1;
    private UUID ratingId;

    @BeforeEach
    void setup() {
        String email1 = "name@gmail.com";
        String email2 = "name@hotmail.com";
        String email3 = "name@mail.utoronto.ca";
        String password = "password";
        String name1 = "name1";
        String name2 = "name2";
        ratingId = UUID.randomUUID();

        user1 = User.builder().name(name2).email(email1).password(password).build();
        User user2 = User.builder().name(name1).email(email2).password(password).build();
        User user3 = User.builder().name(name2).email(email3).password(password).build();
        user1.addRating(rating);
        userList = List.of(user1, user2, user3);
        userNameList = List.of(user1, user3);
    }

    @AfterEach
    public void tearDown() {
        userList = null;
        userNameList = null;
        user1 = null;
        ratingId = null;
    }

    @Test
    void testFindAll() {
        when(repo.findAll()).thenReturn(userList);
        List<User> returnedUsers = findUser.findAll();
        assertIterableEquals(userList, returnedUsers);
    }

    @Test
    void testFindByEmail() {
        when(repo.findByEmail(user1.getEmail())).thenReturn(Optional.ofNullable(user1));
        User returnedUser = findUser.findByEmail(user1.getEmail());
        assertEquals(user1, returnedUser);
    }

    @Test
    void testFindByName() {
        when(repo.findByName("name2")).thenReturn(userNameList);
        List<User> returnedUsers = findUser.findByName("name2");
        assertIterableEquals(userNameList, returnedUsers);
    }

    @Test
    void testFindByRating() {
        when(rating.getId()).thenReturn(ratingId);
        when(repo.findByRatings_id(rating.getId())).thenReturn(Optional.ofNullable(user1));

        User returnedUser = findUser.findByRating(rating.getId());
        assertEquals(user1, returnedUser);
    }

    @Test
    void testUserExistenceCheck() {
        when(repo.findByEmail("name@gmail.com")).thenReturn(Optional.ofNullable(user1));
        when(repo.findByEmail("notinrepo@gmail.com")).thenReturn(Optional.empty());
        assertTrue(findUser.userExistenceCheck("name@gmail.com"));
        assertFalse(findUser.userExistenceCheck("notinrepo@gmail.com"));
    }
}
