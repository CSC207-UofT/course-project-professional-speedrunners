package com.boba.bobabuddy.core.service.item;

import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.service.item.impl.CreateItemServiceImpl;
import com.boba.bobabuddy.core.service.store.FindStoreService;
import com.boba.bobabuddy.core.service.store.UpdateStoreService;
import com.boba.bobabuddy.core.data.dao.ItemJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateItemImplTest {

    @Mock
    private ItemJpaRepository repo;

    @Mock
    private FindStoreService findStore;

    @Mock
    private UpdateStoreService updateStore;

    @Mock
    private Store store;

    @InjectMocks
    private CreateItemServiceImpl createItemImpl;

    @Test
    void testCreate() {
        UUID storeId = UUID.randomUUID();
        //Set up return type for mock repo
        //Note that we are assuming store is returned with findStore.findById, regardless of the actual database status
        when(findStore.findById(storeId)).thenReturn(store);

        ItemDto item = new ItemDto();
        Item item1 = new Item();
        UUID itemId = UUID.randomUUID();
        item.setId(itemId);
        item1.setId(itemId);
        when(repo.save(any())).thenReturn(item1);

        //Execute service call
        Item returnedItem = createItemImpl.create(item, storeId);

        //Assertions
        assertNotNull(returnedItem);
        assertEquals(itemId, returnedItem.getId());
    }


}
