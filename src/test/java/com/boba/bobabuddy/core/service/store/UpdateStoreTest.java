package com.boba.bobabuddy.core.service.store;

import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.store.FindStoreService;
import com.boba.bobabuddy.core.data.dao.StoreJpaRepository;
import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.boba.bobabuddy.core.service.store.impl.UpdateStoreServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
// tell JUnit 5 to enable Spring support
public class UpdateStoreTest {
    private Store store;
    private Item item1;
    private Item item2;

    private UUID storeId;

    @Mock
    //helps to create a mock object, want to use mock dependency so that we can actually test our code
    //mock reduces complexity of our test
    private StoreJpaRepository repo;

    @Mock
    private FindStoreService findStoreService;

    @InjectMocks
    // creates an instance of the class and injects the mocks that are created
    // with the @Mock annotations into this instance.
    private UpdateStoreServiceImpl updateStore;

    @BeforeEach
    void setUp(){
        store = new Store();
        store.setName("Lebron's milk tea");
        store.setLocation("91 Charles St, Toronto, Ontario M5S 1K9");
        store.setId(storeId);

        storeId = UUID.randomUUID();
        store.setId(storeId);

        item1 = new Item();
        item1.setStore(store);
        item1.setName("molk");

        item2 = new Item();
        item2.setStore(store);
        item2.setName("melk");

        store.addItem(item1);
    }

    @AfterEach
    void tearDown(){
        store = null;
    }

    @Test
    void testUpdate() throws DifferentResourceException {
        StoreDto storeDto = new StoreDto();
        storeDto.setName("Kuzma's milk tea");
        storeDto.setLocation("89 Charles St, Toronto, Ontario M5S 1K9");

        Store store1 = new Store();
        store1.setName("Kuzma's milk tea");
        store1.setLocation("89 Charles St, Toronto, Ontario M5S 1K9");

        store1.setId(storeId);
        storeDto.setId(storeId);

        when(repo.save(store)).thenReturn(store);
        when(findStoreService.findById(storeId)).thenReturn(store);
        // making sure that when we call updatestore we have a return value since the repo is mocked.

        Store returnedStore = updateStore.updateStore(storeId, storeDto);

        assertEquals(store1, returnedStore);
        assertEquals(store1.toString(), returnedStore.toString());
        assertNotNull(returnedStore);
    }

    @Test
    void testAddItemToStore() throws DuplicateResourceException {
        when(repo.save(store)).thenReturn(store);

        updateStore.addItem(store, item2);
        assertEquals(2, store.getMenu().size());
        assertTrue(store.getMenu().contains(item2));
    }

    @Test
    void testRemoveItemFromStore() throws ResourceNotFoundException {
        store.addItem(item2);

        when(repo.save(store)).thenReturn(store);

        Store returnedStore = updateStore.removeItem(store, item1);

        assertFalse(returnedStore.getMenu().contains(item1));
        assertTrue(returnedStore.getMenu().contains(item2));
        assertEquals(1, returnedStore.getMenu().size());
    }

    @Test
    void testUpdateStoreImage(){
        when(findStoreService.findById(storeId)).thenReturn(store);
        when(repo.save(store)).thenReturn(store);

        updateStore.updateStoreImage(storeId, "abc.com");

        assertEquals("abc.com", store.getImageUrl());
    }
}