package com.boba.bobabuddy.core.usecase.rating;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.ratableobject.port.IUpdateRatable;
import com.boba.bobabuddy.core.usecase.rating.port.IFindRating;
import com.boba.bobabuddy.infrastructure.dao.RatingJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateRatingTest {
    @Mock
    private RatingJpaRepository repo;

    @Mock
    private IFindRating findRating;

    @Mock
    private IUpdateRatable updateRatable;

    @Mock
    private User user;

    @InjectMocks
    private UpdateRating updateRating;

    private RatableObject ratableObject;

    @Test
    void testUpdateRating() {
        UUID id = UUID.randomUUID();
        Rating rating = new Rating(1, user, ratableObject);

        when(findRating.findById(id)).thenReturn(rating);
        when(repo.save(rating)).thenReturn(rating);

        Rating updatedRating = updateRating.updateRating(id, 0);
        verify(updateRatable, times(1)).updateRating(ratableObject, rating, 1, 0);
        assertEquals(0, updatedRating.getRating());
        assertEquals(rating, updatedRating);
    }
}
