package com.boba.bobabuddy.core.usecase.item;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import com.boba.bobabuddy.infrastructure.dto.ItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateItemTest {

    @Mock
    private ItemJpaRepository repo;

    @Mock
    private Store store;

    @Mock
    private Rating rating;

    @InjectMocks
    private UpdateItem updateItem;

    @Test
    void testUpdate() throws DifferentResourceException {
        Item item1 = new Item(5, store, "milk tea");
        ItemDto item2 = new ItemDto();
        Item item3 = new Item(7, store, "iced tea");
        item2.setPrice(7);
        item2.setName("iced tea");
        UUID itemId = UUID.randomUUID();

        item1.setId(itemId);
        item2.setId(itemId);
        item3.setId(itemId);

        when(repo.save(item1)).thenReturn(item3);

        Item returnedItem = updateItem.updateItem(item1, item2);

        assertEquals(item3, returnedItem);
        assertEquals(item3.toString(), returnedItem.toString());
        assertNotNull(returnedItem);

    }


}
