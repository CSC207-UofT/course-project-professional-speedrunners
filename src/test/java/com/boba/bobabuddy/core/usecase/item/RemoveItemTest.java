package com.boba.bobabuddy.core.usecase.item;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.item.port.IFindItem;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoveItemTest {

    @Mock
    private ItemJpaRepository repo;

    @Mock
    private IFindItem findItem;

    @Mock
    private Store store;

    @InjectMocks
    private RemoveItem removeItem;

    @Test
    void testRemove() throws DifferentResourceException, ResourceNotFoundException {
        Item item1 = new Item(5, store, "milk tea");
        UUID itemId = UUID.randomUUID();
        item1.setId(itemId);
        when(findItem.findById(itemId)).thenReturn(item1);

        removeItem.removeById(itemId);
        verify(repo, Mockito.times(1)).delete(item1);
    }


}