package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.item.port.IFindItem;
import com.boba.bobabuddy.infrastructure.dao.StoreJpaRepository;
import com.boba.bobabuddy.infrastructure.dto.StoreDto;
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

    @Mock
    //helps to create a mock object, want to use mock dependency so that we can actually test our code
    //mock reduces complexity of our test
    private StoreJpaRepository repo;

    @Mock
    private IFindItem findItem;

    @InjectMocks
    // creates an instance of the class and injects the mocks that are created
    // with the @Mock annotations into this instance.
    private UpdateStore updateStore;


    @Test
        // this is not working -,-
    void testUpdate() throws DifferentResourceException {
        Store store1 = new Store("Lebron's milk tea", "91 Charles St, Toronto, Ontario M5S 1K9");
        StoreDto storeDto = new StoreDto();
        storeDto.setName("Kuzma's milk tea");
        storeDto.setLocation("89 Charles St, Toronto, Ontario M5S 1K9");

        Store store2 = new Store("Kuzma's milk tea", "89 Charles St, Toronto, Ontario M5S 1K9");
        UUID storeId = UUID.randomUUID();

        store1.setId(storeId);
        store2.setId(storeId);
        storeDto.setId(storeId);

        assertEquals(store1, store2);

        when(repo.save(store2)).thenReturn(store2);
        // making sure that when we call updatestore we have a return value since the repo is mocked.

        Store returnedStore = updateStore.updateStore(store1, storeDto);

        assertEquals(store2, returnedStore);
        assertEquals(store2.toString(), returnedStore.toString());
        assertNotNull(returnedStore);
    }

    @Test
    void testAddItemToStore() throws DuplicateResourceException {
        Store store2 = new Store("Lebron's milk tea", "91 Charles St, Toronto, Ontario M5S 1K9");
        Item item = new Item(5, store2, "milk tea");

        UUID storeId2 = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        store2.setId(storeId2);
        item.setId(itemId);

        when(repo.save(store2)).thenReturn(store2);

        boolean thrown = false;
        updateStore.addItem(store2, item);
        assertTrue(store2.getMenu().contains(item));
        assertEquals(store2, item.getStore());
    }

    @Test
    void testRemoveItemFromStore() throws ResourceNotFoundException {
        Store store1 = new Store("Shuyi", "75 Charles St, Toronto, Ontario M5S 1K9");
        Item item1 = new Item(5, store1, "milk tea");
        Item item2 = new Item(19, store1, "coconut milk tea");
        UUID storeID = UUID.randomUUID();
        UUID itemId1 = UUID.randomUUID();
        UUID itemId2 = UUID.randomUUID();

        store1.setId(storeID);
        item1.setId(itemId1);
        item2.setId(itemId2);

        when(repo.save(store1)).thenReturn(store1);

        Store returnedStore1 = updateStore.addItem(store1, item1);
        Store returnedStore2 = updateStore.addItem(store1, item2);

        Store returnedStore3 = updateStore.removeItem(store1, item1);

        assertFalse(returnedStore3.getMenu().contains(item1));
        assertTrue(returnedStore3.getMenu().contains(item2));
        assertEquals(1, returnedStore3.getMenu().size());
    }
}
