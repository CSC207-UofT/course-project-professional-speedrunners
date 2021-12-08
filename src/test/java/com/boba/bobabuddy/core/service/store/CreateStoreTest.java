package com.boba.bobabuddy.core.service.store;

import com.boba.bobabuddy.core.data.dao.StoreJpaRepository;
import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.boba.bobabuddy.core.domain.Store;
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

        StoreDto storeDto = new StoreDto();
        storeDto.setName(name);
        storeDto.setLocation(location);

        Store store = new Store();
        store.setLocation(location);
        store.setName(name);

        UUID storeId = UUID.randomUUID();
        storeDto.setId(storeId);
        store.setId(storeId);
        when(repo.save(any())).thenReturn(store);

        Store returnedStore = createStore.create(storeDto);

        assertNotNull(returnedStore);
        assertEquals(storeId, returnedStore.getId());
        assertEquals(location, returnedStore.getLocation());
        assertEquals(name, returnedStore.getName());
    }


}