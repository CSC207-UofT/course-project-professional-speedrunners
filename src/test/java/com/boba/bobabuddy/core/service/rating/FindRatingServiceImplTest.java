package com.boba.bobabuddy.core.service.rating;

import com.boba.bobabuddy.core.domain.*;
import com.boba.bobabuddy.core.service.item.FindItemService;
import com.boba.bobabuddy.core.service.rating.impl.FindRatingServiceImpl;
import com.boba.bobabuddy.core.service.store.FindStoreService;
import com.boba.bobabuddy.core.data.dao.RatingJpaRepository;
import com.boba.bobabuddy.core.service.user.FindUserService;
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
public class FindRatingServiceImplTest {
    @Mock
    Store store;
    @Mock
    Item item;
    @Mock
    private RatingJpaRepository repo;
    @Mock
    private FindItemService findItem;
    @Mock
    private FindStoreService findStore;
    @Mock
    private FindUserService findUser;
    @Mock
    private User user1;
    private RatableObject ratableObject1, ratableObject2;
    @InjectMocks
    private FindRatingServiceImpl findRatingServiceImpl;

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
        rating1 = new Rating();
        rating1.setRating(1);
        rating1.setId(ratingId1);
        Rating rating2 = new Rating();
        rating2.setRating(1);
        Rating rating3 = new Rating();
        rating3.setRating(1);
        Rating rating4 = new Rating();
        rating4.setRating(1);

        ratingSetUser = Set.of(rating1, rating2);
        ratingSetItem = Set.of(rating1, rating3);
        ratingSetStore = Set.of(rating2, rating4);
        ratingLst = List.of(rating1, rating2, rating3, rating4);
    }


    @Test
    void testFindByItem() {
        when(findItem.findById(itemId)).thenReturn((Item) ratableObject1);
        when(ratableObject1.getRatings()).thenReturn(ratingSetItem);

        Set<Rating> returnedRating = findRatingServiceImpl.findByItem(itemId);

        assertIterableEquals(ratingSetItem, returnedRating);

    }

    @Test
    void testFindByStore() {
        when(findStore.findById(storeId)).thenReturn((Store) ratableObject2);
        when(ratableObject2.getRatings()).thenReturn(ratingSetStore);

        Set<Rating> returnedRating = findRatingServiceImpl.findByStore(storeId);

        assertIterableEquals(ratingSetStore, returnedRating);

    }

    @Test
    void testFindByUser() {
        when(findUser.findByEmail(email)).thenReturn(user1);
        when(user1.getRatings()).thenReturn(ratingSetUser);

        Set<Rating> returnedRating = findRatingServiceImpl.findByUser(email);

        assertIterableEquals(ratingSetUser, returnedRating);

    }

    @Test
    void testFindById() {
        when(repo.findById(ratingId1)).thenReturn(Optional.ofNullable(rating1));

        Rating returnedRating = findRatingServiceImpl.findById(ratingId1);

        assertEquals(rating1, returnedRating);

    }

    @Test
    void testFindAll() {
        when(repo.findAll()).thenReturn(ratingLst);

        List<Rating> returnedRating = findRatingServiceImpl.findAll();

        assertIterableEquals(ratingLst, returnedRating);
    }


}
