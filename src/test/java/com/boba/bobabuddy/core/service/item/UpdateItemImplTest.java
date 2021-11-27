package com.boba.bobabuddy.core.service.item;

import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.data.dao.ItemJpaRepository;
import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.service.item.impl.UpdateItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateItemImplTest {

    @Mock
    private ItemJpaRepository repo;

    @Mock
    private Store store;

    @Mock
    private Rating rating;

    @InjectMocks
    private UpdateItemServiceImpl updateItemImpl;

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

        Item returnedItem = updateItemImpl.updateItem(item1, item2);
        assertEquals(item3, returnedItem);
        assertEquals(item3.toString(), returnedItem.toString());
        assertNotNull(returnedItem);

    }


}
