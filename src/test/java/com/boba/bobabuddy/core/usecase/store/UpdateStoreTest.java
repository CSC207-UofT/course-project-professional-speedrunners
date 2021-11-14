package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.infrastructure.database.StoreJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
// tell JUnit 5 to enable Spring support
public class UpdateStoreTest {

    @Mock
    //helps to create a mock object, want to use mock dependency so that we can actually test our code
    //mock reduces complexity of our test
    private StoreJpaRepository repo;

    @Mock
    private Store store;

    @Mock
    private Rating rating;

    @InjectMocks
    // creates an instance of the class and injects the mocks that are created
    // with the @Mock annotations into this instance.
    private UpdateStore updateStore;

    @Test
    // this is not working -,-
    void testUpdate() throws DifferentResourceException{
        Store store1 = new Store("Lebron's milk tea", "91 Charles St, Toronto, Ontario M5S 1K9");
        Store store2 = new Store("Kuzma's milk tea", "89 Charles St, Toronto, Ontario M5S 1K9");
        UUID storeId = UUID.randomUUID();

        store1.setId(storeId);
        store2.setId(storeId);
        assertEquals(store1, store2);

        when(repo.save(store2)).thenReturn(store2);
        // making sure that when we call updatestore we have a return value since the repo is mocked.

        Store returnedStore = updateStore.updateStore(store1, store2);

        assertEquals(store2, returnedStore);
        assertNotNull(returnedStore);
    }

//    @Test
//    void testAddItemToStore() throws DuplicateResourceException{
//        Store store = new Store("Shuyi", "75 Charles St, Toronto, Ontario M5S 1K9");
//        Item item = new Item(5, store, "milk tea");
//        UUID storeID = UUID.randomUUID();
//        UUID itemId = UUID.randomUUID();
//
//        store.setId(storeID);
//        item.setId(itemId);
//
//        when(repo.save(store)).thenReturn(store);
//        when(store.addItem(item)).then
//    }
//
//    @Test
//    void testRemoveItemFromStore() throws ResourceNotFoundException{
//
    }

}
