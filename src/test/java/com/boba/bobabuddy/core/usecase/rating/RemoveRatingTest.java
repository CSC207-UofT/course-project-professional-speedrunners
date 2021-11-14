package com.boba.bobabuddy.core.usecase.rating;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.ratableobject.port.IUpdateRatable;
import com.boba.bobabuddy.core.usecase.rating.port.IFindRating;
import com.boba.bobabuddy.infrastructure.database.RatingJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RemoveRatingTest {
    @Mock
    private RatingJpaRepository repo;

    @Mock
    private IFindRating findRating;

    @Mock
    private IUpdateRatable updateRatable;

    @InjectMocks
    private RemoveRating removeRating;

    @Mock
    private Rating rating;

    private RatableObject ratableObject;

    @Test
    void testRemoveById() {
        UUID id = UUID.randomUUID();
        when(findRating.findById(id)).thenReturn(rating);

        removeRating.removeById(id);
        verify(updateRatable, times(1)).removeRating(ratableObject, rating);
        verify(repo, times(1)).delete(rating);
    }
}
