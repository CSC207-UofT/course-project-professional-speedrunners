package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.store.port.IFindStore;
import com.boba.bobabuddy.infrastructure.database.StoreJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoveStoreTest {

    @Mock
    //helps to create a mock object, want to use mock dependency so that we can actually test our code
    //mock reduces complexity of our test
    private StoreJpaRepository repo;

    @Mock
    private IFindStore findStore;

    @InjectMocks
    // creates an instance of the class and injects the mocks that are created
    // with the @Mock annotations into this instance.
    private RemoveStore removeStore;

    @Test
    void testRemove() throws DifferentResourceException, ResourceNotFoundException{
        Store store1 = new Store("M.Jordan's fav milk tea", "75 Charles St, Toronto, Ontario M5S 1K9");
        Item item1 = new Item(5, store1, "milk tea");

        UUID storeId1 = UUID.randomUUID();
        UUID itemId1 = UUID.randomUUID();

        store1.setId(storeId1);
        item1.setId(itemId1);

        when(findStore.findById(storeId1)).thenReturn(store1);

        removeStore.removeById(storeId1);
        verify(repo, Mockito.times(1)).delete(store1);
    }
}
