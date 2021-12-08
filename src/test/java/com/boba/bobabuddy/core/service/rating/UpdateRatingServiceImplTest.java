package com.boba.bobabuddy.core.service.rating;

import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.service.ratableobject.FindRatableService;
import com.boba.bobabuddy.core.service.ratableobject.UpdateRatableService;
import com.boba.bobabuddy.core.service.rating.impl.UpdateRatingServiceImpl;
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
public class UpdateRatingServiceImplTest {
    @Mock
    private RatingJpaRepository repo;

    @Mock
    private FindRatingService findRating;

    @Mock
    private UpdateRatableService updateRatable;

    @Mock
    private FindRatableService findRatable;

    @InjectMocks
    private UpdateRatingServiceImpl updateRatingServiceImpl;

    private RatableObject ratableObject;

    @Test
    void testUpdateRating() {
        UUID id = UUID.randomUUID();
        Rating rating = new Rating();
        rating.setRating(1);
        rating.setId(id);

        when(findRating.findById(id)).thenReturn(rating);
        when(repo.save(rating)).thenReturn(rating);
        when(findRatable.findByRating(id)).thenReturn(ratableObject);

        Rating updatedRating = updateRatingServiceImpl.updateRating(id, 0);
        verify(updateRatable, times(1)).updateRating(ratableObject, rating, 1, 0);
        assertEquals(0, updatedRating.getRating());
        assertEquals(rating, updatedRating);
    }
}
