package com.boba.bobabuddy.core.service.rating;

import com.boba.bobabuddy.core.data.dto.RatingDto;
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
import static org.mockito.Mockito.*;

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
    private String email;
    private String ratableType;

    @InjectMocks
    private CreateRatingServiceImpl createRatingServiceImpl;

    @Test
    void testCreate() {
        UUID ratableId = UUID.randomUUID();
        Rating rating = new Rating();
        rating.setRating(1);
        RatingDto ratingDto = new RatingDto();
        ratingDto.setRating(1);

        when(findUser.findByEmail(email)).thenReturn(user);
        when(findRatable.findByTypeAndId(ratableType, ratableId)).thenReturn(ratableObject);
        when(repo.save(any())).thenReturn(rating);

        Rating returnedRating = createRatingServiceImpl.create(ratingDto, ratableType, ratableId, email);


        assertEquals(rating, returnedRating);
    }
}
