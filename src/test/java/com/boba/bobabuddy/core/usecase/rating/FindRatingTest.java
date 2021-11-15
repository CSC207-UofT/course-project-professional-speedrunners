package com.boba.bobabuddy.core.usecase.rating;

import com.boba.bobabuddy.core.entity.*;
import com.boba.bobabuddy.core.usecase.item.port.IFindItem;
import com.boba.bobabuddy.core.usecase.store.port.IFindStore;
import com.boba.bobabuddy.infrastructure.dao.RatingJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindRatingTest {
    @Mock
    Store store;
    @Mock
    Item item;
    @Mock
    private RatingJpaRepository repo;
    @Mock
    private IFindItem findItem;
    @Mock
    private IFindStore findStore;
    @Mock
    private User user1, user2;
    private RatableObject ratableObject1, ratableObject2;
    @InjectMocks
    private FindRating findRating;

    private Rating rating1;

    private Set<Rating> ratingSetUser, ratingSetItem, ratingSetStore;

    private List<Rating> ratingLst;

    private UUID ratingId1, itemId, storeId;

    private String email;

    @BeforeEach
    void setup() {
        ratingId1 = UUID.randomUUID();
        itemId = UUID.randomUUID();
        storeId = UUID.randomUUID();
        email = "leo@gmail.com";
        ratableObject1 = item;
        ratableObject2 = store;
        rating1 = new Rating(1, user1, ratableObject1);
        Rating rating2 = new Rating(1, user1, ratableObject2);
        Rating rating3 = new Rating(1, user2, ratableObject1);
        Rating rating4 = new Rating(1, user2, ratableObject2);

        ratingSetUser = Set.of(rating1, rating2);
        ratingSetItem = Set.of(rating1, rating3);
        ratingSetStore = Set.of(rating2, rating4);
        ratingLst = List.of(rating1, rating2, rating3, rating4);
    }


    @Test
    void testFindByItem() {
        when(findItem.findById(itemId)).thenReturn((Item) ratableObject1);
        when(ratableObject1.getRatings()).thenReturn(ratingSetItem);

        Set<Rating> returnedRating = findRating.findByItem(itemId);

        assertIterableEquals(ratingSetItem, returnedRating);

    }

    @Test
    void testFindByStore() {
        when(findStore.findById(storeId)).thenReturn((Store) ratableObject2);
        when(ratableObject2.getRatings()).thenReturn(ratingSetStore);

        Set<Rating> returnedRating = findRating.findByStore(storeId);

        assertIterableEquals(ratingSetStore, returnedRating);

    }

    @Test
    void testFindByUser() {
        when(repo.findByUser_email(email)).thenReturn(ratingSetUser);

        Set<Rating> returnedRating = findRating.findByUser(email);

        assertIterableEquals(ratingSetUser, returnedRating);

    }

    @Test
    void testFindById() {
        when(repo.findById(ratingId1)).thenReturn(Optional.ofNullable(rating1));

        Rating returnedRating = findRating.findById(ratingId1);

        assertEquals(rating1, returnedRating);

    }

    @Test
    void testFindAll() {
        when(repo.findAll()).thenReturn(ratingLst);

        List<Rating> returnedRating = findRating.findAll();

        assertIterableEquals(ratingLst, returnedRating);
    }


}
