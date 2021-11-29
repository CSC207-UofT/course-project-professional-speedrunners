package com.boba.bobabuddy.core.service.store;

import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.data.dao.StoreJpaRepository;
import com.boba.bobabuddy.core.service.store.impl.CreateStoreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateStoreTest {

    @Mock
    private StoreJpaRepository repo;

    @InjectMocks
    private CreateStoreServiceImpl createStore;

    @Test
    void testCreate() {
        String name = "Boba shop";
        String location = "123 street";

        Store store = new Store(name, location);
        UUID storeId = UUID.randomUUID();
        store.setId(storeId);
        when(repo.save(any())).thenReturn(store);

        Store returnedStore = createStore.create(store);

        assertNotNull(returnedStore);
        assertEquals(storeId, returnedStore.getId());
        assertEquals(location, returnedStore.getLocation());
        assertEquals(name, returnedStore.getName());
    }


}