package com.boba.bobabuddy.core.entitiy;

import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RatableObjectTest {
    private RatableObject ratableObject;

    private Rating rating1, rating2, rating3;

    private Set<Rating> ratings;

    @BeforeEach
    void setup() {
        ratableObject = Mockito.mock(RatableObject.class, Mockito.CALLS_REAL_METHODS);
        rating1 = new Rating();
        rating1.setRating(1);
        rating2 = new Rating();
        rating2.setRating(0);
        rating3 = new Rating();
        rating3.setRating(1);
        ratings = new HashSet<>();
    }

    @Test
    void testAddRating() {
        ratableObject.setRatings(ratings);
        assertTrue(ratableObject.addRating(rating1));
        assertEquals(1, ratableObject.getAvgRating());
        assertTrue(ratableObject.addRating(rating2));
        assertEquals(0.5, ratableObject.getAvgRating());
        assertFalse(ratableObject.addRating(rating2));
        assertEquals(0.5, ratableObject.getAvgRating());
        assertTrue(ratableObject.addRating(rating3));
        assertEquals((double) 2 / 3, ratableObject.getAvgRating());
    }

    @Test
    void testRemoveRating() {
        ratings.add(rating1);
        ratings.add(rating2);
        ratings.add(rating3);
        ratableObject.setRatings(ratings);
        assertTrue(ratableObject.removeRating(rating1));
        assertEquals(0.5, ratableObject.getAvgRating());
        assertTrue(ratableObject.removeRating(rating2));
        assertEquals(1, ratableObject.getAvgRating());
        assertTrue(ratableObject.removeRating(rating3));
        assertEquals(0, ratableObject.getAvgRating());
        assertFalse(ratableObject.removeRating(rating3));
    }

}
