package com.boba.bobabuddy.core.usecase.rating;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.ratableobject.port.IFindRatable;
import com.boba.bobabuddy.core.usecase.ratableobject.port.IUpdateRatable;
import com.boba.bobabuddy.core.usecase.user.port.IFindUser;
import com.boba.bobabuddy.core.usecase.user.port.IUpdateUser;
import com.boba.bobabuddy.infrastructure.dao.RatingJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateRatingTest {

    @Mock
    private RatingJpaRepository repo;
    @Mock
    private IFindRatable findRatable;
    @Mock
    private IUpdateRatable updateRatable;
    @Mock
    private IFindUser findUser;
    @Mock
    private IUpdateUser updateUser;
    @Mock
    private RatableObject ratableObject;
    @Mock
    private User user;

    @InjectMocks
    private CreateRating createRating;

    @Test
    void testCreate() {
        when(findUser.findByEmail(any())).thenReturn(user);
        when(findRatable.findById(any())).thenReturn(ratableObject);


        UUID id = UUID.randomUUID();
        Rating rating = new Rating(1, user, ratableObject);
        rating.setId(id);
        when(repo.save(rating)).thenReturn(rating);


        Rating returnedRating = createRating.create(rating, ratableObject.getId(), user.getEmail());

        assertEquals(rating, returnedRating);


    }
}
