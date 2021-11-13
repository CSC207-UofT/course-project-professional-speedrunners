package com.boba.bobabuddy.core.usecase.item;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.item.CreateItem;
import com.boba.bobabuddy.core.usecase.item.port.ICreateItem;
import com.boba.bobabuddy.core.usecase.store.port.IFindStore;
import com.boba.bobabuddy.core.usecase.store.port.IUpdateStore;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import com.boba.bobabuddy.infrastructure.database.StoreJpaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateItemTest {

    @Mock
    private ItemJpaRepository repo;

    @Mock
    private IFindStore findStore;

    @Mock
    private IUpdateStore updateStore;

    @Mock
    private Store store;

    @InjectMocks
    private CreateItem createItem;

    @Test
    void testCreate(){
        UUID storeId = UUID.randomUUID();
        //Set up return type for mock repo
        //Note that we are assuming store is returned with findStore.findById, regardless of the actual database status
        when(findStore.findById(storeId)).thenReturn(store);

        Item item = new Item(5, store, "milk tea");
        UUID itemId = UUID.randomUUID();
        item.setId(itemId);
        when(repo.save(any())).thenReturn(item);

        //Execute service call
        Item returnedItem = createItem.create(item, storeId);

        //Assertions
        assertNotNull(returnedItem);
        assertEquals(itemId, returnedItem.getId());
    }


}
