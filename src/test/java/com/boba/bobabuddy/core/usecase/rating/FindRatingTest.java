package com.boba.bobabuddy.core.usecase.rating;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.ratableobject.port.IFindRatable;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class FindRatingTest {
    @Mock
    private RatingJpaRepository repo;
    @Mock
    private IFindRatable findRatable;

    @Mock
    private User user1, user2;

    @Mock
    private RatableObject ratableObject1, ratableObject2;

    @InjectMocks
    private FindRating findRating;

    private Rating rating1;

    private Set<Rating> ratingSetUser, ratingSetRatable;

    private List<Rating> ratingLst;

    private UUID id;

    private String email;

    @BeforeEach
    void setup(){
        id = UUID.randomUUID();
        email = "leo@gmail.com";
        rating1 = new Rating(1, user1, ratableObject1);
        Rating rating2 = new Rating(1, user1, ratableObject2);
        Rating rating3 = new Rating(1, user2, ratableObject1);
        Rating rating4 = new Rating(1, user2, ratableObject2);

        ratingSetUser = Set.of(rating1, rating2);
        ratingSetRatable = Set.of(rating1, rating3);
        ratingLst = List.of(rating1, rating2, rating3, rating4);
    }


    @Test
    void testFindByRatableObject(){
        when(findRatable.findById(id)).thenReturn(ratableObject1);
        when(ratableObject1.getRatings()).thenReturn(ratingSetRatable);

        Set<Rating> returnedRating = findRating.findByRatableObject(id);

        assertIterableEquals(ratingSetRatable, returnedRating);

    }

    @Test
    void testFindByUser(){
        when(repo.findByUser_email(email)).thenReturn(ratingSetUser);

        Set<Rating> returnedRating = findRating.findByUser(email);

        assertIterableEquals(ratingSetUser, returnedRating);

    }

    @Test
    void testFindById(){
        when(repo.findById(id)).thenReturn(Optional.ofNullable(rating1));

        Rating returnedRating = findRating.findById(id);

        assertEquals(rating1, returnedRating);

    }

    @Test
    void testFindAll(){
        when(repo.findAll()).thenReturn(ratingLst);

        List<Rating> returnedRating = findRating.findAll();

        assertIterableEquals(ratingLst, returnedRating);
    }


}
