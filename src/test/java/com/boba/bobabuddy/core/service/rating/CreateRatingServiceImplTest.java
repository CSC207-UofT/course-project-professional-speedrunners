package com.boba.bobabuddy.core.service.rating;

import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.service.ratableobject.FindRatableService;
import com.boba.bobabuddy.core.service.ratableobject.UpdateRatableService;
import com.boba.bobabuddy.core.service.rating.impl.CreateRatingServiceImpl;
import com.boba.bobabuddy.core.service.user.FindUserService;
import com.boba.bobabuddy.core.service.user.UpdateUserService;
import com.boba.bobabuddy.core.data.dao.RatingJpaRepository;
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
public class CreateRatingServiceImplTest {

    @Mock
    private RatingJpaRepository repo;
    @Mock
    private FindRatableService findRatable;
    @Mock
    private UpdateRatableService updateRatable;
    @Mock
    private FindUserService findUser;
    @Mock
    private UpdateUserService updateUser;
    @Mock
    private RatableObject ratableObject;
    @Mock
    private User user;

    @InjectMocks
    private CreateRatingServiceImpl createRatingServiceImpl;

    @Test
    void testCreate() {
        when(findUser.findByEmail(any())).thenReturn(user);
        when(findRatable.findById(any())).thenReturn(ratableObject);


        UUID id = UUID.randomUUID();
        Rating rating = new Rating(1, user, ratableObject);
        rating.setId(id);
        when(repo.save(rating)).thenReturn(rating);


        Rating returnedRating = createRatingServiceImpl.create(rating, ratableType, ratableObject.getId(), user.getEmail());

        assertEquals(rating, returnedRating);


    }
}
