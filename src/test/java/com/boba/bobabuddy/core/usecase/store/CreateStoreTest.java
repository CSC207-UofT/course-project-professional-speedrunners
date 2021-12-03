package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.infrastructure.dao.StoreJpaRepository;
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
    private CreateStore createStore;

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