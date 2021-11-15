package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.user.port.IFindUser;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateUserTest {
    @Mock
    private UserJpaRepository repo;

    @Mock
    private Rating rating;

    @Mock
    private IFindUser findUser;

    @InjectMocks
    private UpdateUser updateUser;

    private User user1, user2;
    String email;

    @BeforeEach
    void setup(){
        email = "name@gmail.com";
        String name1 = "name1";
        String password1 = "password1";
        String name2 = "name2";
        String password2 = "password2";

        user1 = new User(name1, email, password1);
        user2 = new User(name2, email, password2);
        user1.addRating(rating);
    }

    @AfterEach
    void tearDown(){
        user1 = null;
        user2 = null;
        email = null;
    }

    @Test
    void testUpdateUser(){
        when(findUser.findByEmail(email)).thenReturn(user1);
        when(repo.save(user1)).thenReturn(user1);
        User returnedUser = updateUser.updateUser(findUser.findByEmail(email), user2);
        assertEquals(returnedUser.getName(), user2.getName());
        assertEquals(returnedUser.getPassword(), user2.getPassword());
        assertEquals(returnedUser.getRatings(), user2.getRatings());
    }
    @Test
    void testAddRating(){
        when(findUser.findByEmail(email)).thenReturn(user2);
        when(repo.save(user2)).thenReturn(user2);
        Set<Rating> emptyRatingList = Collections.emptySet();
        Set<Rating> updatedRatingList = Set.of(rating);
        assertEquals(user2.getRatings(), emptyRatingList);
        User returnedUser = updateUser.addRating(findUser.findByEmail(email), rating);
        assertEquals(returnedUser.getRatings(), updatedRatingList);
    }
}
