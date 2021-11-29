package com.boba.bobabuddy.core.service.item;

import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.item.impl.RemoveItemServiceImpl;
import com.boba.bobabuddy.core.data.dao.ItemJpaRepository;
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
public class RemoveItemImplTest {

    @Mock
    private ItemJpaRepository repo;

    @Mock
    private FindItemService findItem;

    @Mock
    private Store store;

    @InjectMocks
    private RemoveItemServiceImpl removeItemImpl;

    @Test
    void testRemove() throws DifferentResourceException, ResourceNotFoundException {
        Item item1 = new Item(5, store, "milk tea");
        UUID itemId = UUID.randomUUID();
        item1.setId(itemId);
        when(findItem.findById(itemId)).thenReturn(item1);

        removeItemImpl.removeById(itemId);
        verify(repo, Mockito.times(1)).delete(item1);
    }


}