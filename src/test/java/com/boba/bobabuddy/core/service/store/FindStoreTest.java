package com.boba.bobabuddy.core.service.store;

import com.boba.bobabuddy.core.domain.*;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.item.impl.FindItemServiceImpl;
import com.boba.bobabuddy.core.data.dao.StoreJpaRepository;
import com.boba.bobabuddy.core.service.store.impl.FindStoreServiceImpl;
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
class FindStoreTest {
    private UUID storeId;
    private UUID ratingId;
    private UUID itemId;
    private String location;
    private String name;

    @Mock
    private Sort sort;

    @Mock
    private Item item;

    @Mock
    private Rating rating;

    @Mock
    private StoreJpaRepository repo;

    @Mock
    private User user;

    @Mock
    private FindItemServiceImpl findItemImpl;

    @InjectMocks
    private FindStoreServiceImpl findStore;

    private Store store;
    private List<Store> allStoreLst;
    private List<Store> storeLst;


    @BeforeEach
    void setUp() {
        storeId = UUID.randomUUID();
        ratingId = UUID.randomUUID();
        itemId = UUID.randomUUID();
        name = "Boba shop";
        location = "123 street";

        store = new Store();
        store.setName(name);

        Store store1 = new Store();
        store1.setLocation("bb street");
        store1.setName("bb");
        Store store2 = new Store();
        store2.setLocation("bb street");
        store2.setName("cc");

        Set<Category> set = new HashSet<>();
        item = new Item(5, store, set);

        store.addItem(item);
        store.setId(storeId);
        store.addRating(rating);
        store.setAvgRating(1);
        store1.setAvgRating(0.5F);
        store2.setAvgRating(0.5F);

        allStoreLst = Arrays.asList(store, store1, store2);
        storeLst = List.of(store);
    }

    @AfterEach
    public void tearDown() {
        store = null;
        allStoreLst = null;
        storeLst = null;
        storeId = null;
        ratingId = null;
    }

    @Test
    void findAll() {
        when(repo.findAll(sort)).thenReturn(storeLst);
        List<Store> returnedStores = findStore.findAll(sort);
        assertIterableEquals(storeLst, returnedStores);
        assertNotNull(returnedStores);
    }

    @Test
    void findByLocation() {
        when(repo.findByLocation("123 street", sort)).thenReturn(storeLst);

        List<Store> returnedStores = findStore.findByLocation("123 street", sort);
        assertIterableEquals(storeLst, returnedStores);
        assertNotNull(returnedStores);
    }

    @Test
    void findById() throws ResourceNotFoundException {
        when(repo.findById(storeId)).thenReturn(java.util.Optional.ofNullable(store));

        Store returnedStore = findStore.findById(storeId);
        assertEquals(store, returnedStore);
        assertNotNull(returnedStore);
    }

    @Test
    void findByName() {
        when(repo.findByNameIgnoreCase("Boba shop", sort)).thenReturn(storeLst);

        List<Store> returnedStores = findStore.findByName("Boba shop", sort);
        assertIterableEquals(storeLst, returnedStores);
        assertNotNull(returnedStores);
    }

    @Test
    void findByNameContaining() {
        when(repo.findByNameContainingIgnoreCase("Boba shop", sort)).thenReturn(storeLst);

        List<Store> returnedStores = findStore.findByNameContaining("Boba shop", sort);
        assertIterableEquals(storeLst, returnedStores);
        assertNotNull(returnedStores);
    }

    @Test
    void findByAvgRatingGreaterThanEqual() {
        when(repo.findByAvgRatingGreaterThanEqual(1, Sort.by("avgRating").descending())).thenReturn(storeLst);

        List<Store> returnedStores = findStore.findByAvgRatingGreaterThanEqual(1, Sort.by("avgRating").descending());
        assertIterableEquals(storeLst, returnedStores);
        assertNotNull(returnedStores);
    }

    @Test
    void findByItem() throws ResourceNotFoundException {
        when(findItemImpl.findById(itemId)).thenReturn(item);

        Store returnedStores = findStore.findByItem(itemId);
        assertEquals(store, returnedStores);
        assertNotNull(returnedStores);
    }

    @Test
    void findByRating() throws ResourceNotFoundException {
        when(rating.getId()).thenReturn(ratingId);
        when(repo.findByRatings_id(rating.getId())).thenReturn(Optional.ofNullable(store));

        Store returnedStore = findStore.findByRating(rating.getId());
        assertEquals(store, returnedStore);
        assertNotNull(returnedStore);
    }
}
