package com.boba.bobabuddy.core.service.item;

import com.boba.bobabuddy.core.data.dao.ItemJpaRepository;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.item.impl.FindItemServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindItemImplTest {

    @Mock
    private ItemJpaRepository repo;

    @Mock
    private Sort sort;

    @Mock
    private Store store;

    @Mock
    private Rating rating;

    @InjectMocks
    private FindItemServiceImpl findItemImpl;

    private Item item1;

    private List<Item> itemLst, itemLstRating, itemLstName;

    private UUID storeId;
    private UUID ratingId;
    private UUID itemId1;

    @BeforeEach
    public void setup() {
        storeId = UUID.randomUUID();
        ratingId = UUID.randomUUID();
        itemId1 = UUID.randomUUID();
        UUID itemId2 = UUID.randomUUID();
        UUID itemId3 = UUID.randomUUID();

        Set<Category> set = new HashSet<>();
        item1 = new Item(5, store, set);
        Item item2 = new Item(7, store, set);
        Item item3 = new Item(9, store, set);


        item1.setId(itemId1);
        item1.addRating(rating);
        item1.setAvgRating(0);

        item2.setId(itemId2);
        item2.setAvgRating(0.5F);
        item3.setId(itemId3);
        item3.setAvgRating(1);

        itemLst = Arrays.asList(item1, item2, item3);
        itemLstRating = Arrays.asList(item3, item2, item1);
        itemLstName = List.of(item1);
    }

    @AfterEach
    public void tearDown() {
        item1 = null;
        itemLst = null;
        itemLstRating = null;
        itemLstName = null;
        storeId = null;
        ratingId = null;
        itemId1 = null;
    }

    @Test
    void testFindByStore() {
        when(repo.findByStore_id(storeId, sort)).thenReturn(itemLst);
        when(store.getId()).thenReturn(storeId);


        List<Item> returnedItem = findItemImpl.findByStore(store.getId(), sort);
        assertIterableEquals(itemLst, returnedItem);
        assertNotNull(returnedItem);

    }

    @Test
    void testFindAll() {
        when(repo.findAll(sort)).thenReturn(itemLst);

        List<Item> returnedItem = findItemImpl.findAll(sort);
        assertIterableEquals(itemLst, returnedItem);
        assertNotNull(returnedItem);
    }

    @Test
    void testFindByName() {
        when(repo.findByNameIgnoreCase("milk tea", sort)).thenReturn(itemLstName);

        List<Item> returnedItem = findItemImpl.findByName("milk tea", sort);
        assertIterableEquals(itemLstName, returnedItem);
        assertNotNull(returnedItem);

    }

    @Test
    void testFindByNameContaining() {
        when(repo.findByNameContainingIgnoreCase("tea", sort)).thenReturn(itemLst);

        List<Item> returnedItem = findItemImpl.findByNameContaining("tea", sort);
        assertIterableEquals(itemLst, returnedItem);
        assertNotNull(returnedItem);

    }

    @Test
    void testFindByRating() throws ResourceNotFoundException {
        when(rating.getId()).thenReturn(ratingId);
        when(repo.findByRatings_id(rating.getId())).thenReturn(Optional.ofNullable(item1));

        Item returnedItem = findItemImpl.findByRating(rating.getId());
        assertEquals(item1, returnedItem);
        assertNotNull(returnedItem);

    }

    @Test
    void testFindById() throws ResourceNotFoundException {
        when(repo.findById(itemId1)).thenReturn(Optional.ofNullable(item1));

        Item returnedItem = findItemImpl.findById(itemId1);
        assertEquals(item1, returnedItem);
        assertNotNull(returnedItem);


    }

    @Test
    void testFindByPriceLessThanEqual() {
        when(repo.findByPriceLessThanEqual(10, Sort.by("price").ascending())).thenReturn(itemLst);

        List<Item> returnedItem = findItemImpl.findByPriceLessThanEqual(10,Sort.by("price").ascending());
        assertIterableEquals(itemLst, returnedItem);
        assertNotNull(returnedItem);
    }

    @Test
    void testFindBy() {
        when(repo.findByAvgRatingGreaterThanEqual(0, Sort.by("avgRating").descending())).thenReturn(itemLstRating);

        List<Item> returnedItem = findItemImpl.findByAvgRatingGreaterThanEqual(0, Sort.by("avgRating").descending());
        assertIterableEquals(itemLstRating, returnedItem);
        assertNotNull(returnedItem);
    }
}
