package com.boba.bobabuddy.core.usecase;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.item.CreateItem;
import com.boba.bobabuddy.core.usecase.item.port.ICreateItem;
import com.boba.bobabuddy.core.usecase.store.FindStore;
import com.boba.bobabuddy.core.usecase.store.port.IFindStore;
import com.boba.bobabuddy.core.usecase.store.port.IUpdateStore;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import com.boba.bobabuddy.infrastructure.database.StoreJpaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class CreateItemTest {

    @MockBean
    private ItemJpaRepository repo;

    @MockBean
    private StoreJpaRepository storeRepo;

    @MockBean
    @Autowired
    private IFindStore findStore;

    @MockBean
    @Autowired
    private IUpdateStore updateStore;

    @MockBean
    private Store store;

    @Autowired
    private ICreateItem createItem;

    @Test
    void testCreate() throws ResourceNotFoundException, DuplicateResourceException {
        UUID storeId = UUID.randomUUID();
        store.setId(storeId);
        doReturn(store).when(storeRepo).findById(storeId);
        Item item = new Item(5, store, "milk tea");
        UUID itemId = UUID.randomUUID();
        item.setId(itemId);
        doReturn(Optional.of(item)).when(repo).save(any());

        Item returnedItem = createItem.create(item, storeId);

        Assertions.assertNotNull(returnedItem);
        Assertions.assertEquals(itemId, returnedItem.getId());

    }


}
