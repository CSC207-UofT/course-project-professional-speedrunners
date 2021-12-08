package com.boba.bobabuddy.core.service.store;

import com.boba.bobabuddy.core.data.dao.StoreJpaRepository;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.store.impl.RemoveStoreServiceImpl;
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
    private FindStoreService findStore;

    @InjectMocks
    // creates an instance of the class and injects the mocks that are created
    // with the @Mock annotations into this instance.
    private RemoveStoreServiceImpl removeStore;

    @Test
    void testRemove() throws DifferentResourceException, ResourceNotFoundException {
        Store store = new Store();
        Item item = new Item();
        item.setStore(store);

        UUID storeId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        store.setId(storeId);
        item.setId(itemId);

        when(findStore.findById(storeId)).thenReturn(store);

        removeStore.removeById(storeId);
        verify(repo, Mockito.times(1)).delete(store);
    }
}
