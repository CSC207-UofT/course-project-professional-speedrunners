package com.boba.bobabuddy.core.service.rating;

import com.boba.bobabuddy.core.data.dao.RatingJpaRepository;
import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.service.ratableobject.FindRatableService;
import com.boba.bobabuddy.core.service.ratableobject.UpdateRatableService;
import com.boba.bobabuddy.core.service.rating.impl.RemoveRatingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RemoveRatingServiceImplTest {
    @Mock
    private RatingJpaRepository repo;

    @Mock
    private FindRatingService findRating;

    @Mock
    private UpdateRatableService updateRatable;

    @Mock
    private FindRatableService findRatableService;

    @InjectMocks
    private RemoveRatingServiceImpl removeRatingServiceImpl;

    @Mock
    private Rating rating;
    @Mock
    private RatableObject ratableObject;

    @Test
    void testRemoveById() {
        UUID id = UUID.randomUUID();
        when(findRating.findById(id)).thenReturn(rating);
        when(rating.getId()).thenReturn(id);
        when(findRatableService.findByRating(id)).thenReturn(ratableObject);

        removeRatingServiceImpl.removeById(id);
        verify(updateRatable, times(1)).removeRating(ratableObject, rating);
        verify(repo, times(1)).delete(rating);
    }
}
