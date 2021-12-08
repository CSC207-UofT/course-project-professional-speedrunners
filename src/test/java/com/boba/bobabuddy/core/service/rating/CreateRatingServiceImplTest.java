package com.boba.bobabuddy.core.service.rating;

import com.boba.bobabuddy.core.data.dao.RatingJpaRepository;
import com.boba.bobabuddy.core.data.dto.RatingDto;
import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.service.ratableobject.FindRatableService;
import com.boba.bobabuddy.core.service.ratableobject.UpdateRatableService;
import com.boba.bobabuddy.core.service.rating.impl.CreateRatingServiceImpl;
import com.boba.bobabuddy.core.service.user.FindUserService;
import com.boba.bobabuddy.core.service.user.UpdateUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

    @InjectMocks
    private CreateRatingServiceImpl createRatingServiceImpl;

    @Test
    void testCreate() {
        when(findUser.findByEmail(any())).thenReturn(user);
        when(findRatable.findByTypeAndId(any(), any())).thenReturn(ratableObject);


        UUID id = UUID.randomUUID();
        RatingDto ratingDto = RatingDto.builder().rating(1).id(id).build();
        Rating rating = Rating.builder().rating(1).build();
        Rating createdRating = Rating.builder().rating(1).id(id).build();

        when(repo.save(any())).thenReturn(createdRating);


        Rating returnedRating = createRatingServiceImpl.create(ratingDto, "", ratableObject.getId(), user.getEmail());

        assertEquals(createdRating, returnedRating);
        verify(updateUser, times(1)).addRating(user, createdRating);
        verify(updateRatable, times(1)).addRating(ratableObject, createdRating);


    }
}
