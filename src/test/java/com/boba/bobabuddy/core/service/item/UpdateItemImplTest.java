package com.boba.bobabuddy.core.service.item;

import com.boba.bobabuddy.core.data.dao.ItemJpaRepository;
import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.category.FindCategoryService;
import com.boba.bobabuddy.core.service.item.impl.UpdateItemServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateItemImplTest {

    @Mock
    private ItemJpaRepository repo;

    @Mock
    private FindItemService findItemService;

    @Mock
    private FindCategoryService findCategoryService;

    private Category c1;
    private Category c2;
    private UUID itemId;
    private Item cItem;

    @Mock
    private Rating rating;

    @InjectMocks
    private UpdateItemServiceImpl updateItemImpl;

    @BeforeEach
    void setUp(){
        cItem = new Item();
        cItem.setName("bb");
        cItem.setPrice(10);
        itemId = UUID.randomUUID();
        cItem.setId(itemId);

        HashSet<Item> set1 = new HashSet<>();
        set1.add(cItem);

        c1 = new Category();
        c1.setItems(set1);
        c1.setName("milk tea");
        c1.setId(UUID.randomUUID());

        c2 = new Category();
        c1.setItems(set1);
        c2.setName("slush");
        c2.setId(UUID.randomUUID());

        HashSet<Category> set = new HashSet<>();
        set.add(c1);
        cItem.setCategories(set);
    }

    @AfterEach
    void tearDown(){
        cItem = null;
    }


    @Test
    void testUpdate() throws DifferentResourceException {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("bb");
        UUID itemId = UUID.randomUUID();
        itemDto.setId(itemId);

        Item item1 = new Item();
        item1.setName("bb");
        item1.setId(itemId);

        Item item2 = new Item();
        item2.setName("cc");
        item2.setId(itemId);


        when(findItemService.findById(itemId)).thenReturn(item2);
        when(repo.save(item2)).thenReturn(item2);

        Item returnedItem = updateItemImpl.updateItem(item2.getId(), itemDto);

        assertEquals(item1, returnedItem);
        assertEquals(item1.toString(), returnedItem.toString());
        assertNotNull(returnedItem);
    }

    @Test
    void testUpdateItemPrice() throws IllegalArgumentException{
        Item item = new Item();
        item.setPrice(5);
        UUID itemId = UUID.randomUUID();
        item.setId(itemId);

        ItemDto itemDto = new ItemDto();
        itemDto.setPrice(5);
        itemDto.setId(itemId);

        when(repo.save(any())).thenReturn(item);
        when(findItemService.findById(itemId)).thenReturn(item);

        Item returnedItem = updateItemImpl.updateItemPrice(itemDto.getId(), 10);
        assertNotNull(returnedItem);
        assertEquals(10, item.getPrice());
    }

    @Test
    void testAddCategory() throws DuplicateResourceException{
        when(repo.save(cItem)).thenReturn(cItem);
        when(findItemService.findById(itemId)).thenReturn(cItem);
        when(findCategoryService.findByName("slush")).thenReturn(c2);

        updateItemImpl.addCategory(itemId, "slush");
        assertEquals(2, cItem.getCategories().size());
        assertTrue(cItem.getCategories().contains(c2));
    }

    @Test
    void testRemoveCategory() throws ResourceNotFoundException{
        when(repo.save(cItem)).thenReturn(cItem);
        when(findItemService.findById(itemId)).thenReturn(cItem);
        when(findCategoryService.findByName("milk tea")).thenReturn(c1);
        cItem.addCategory(c2);

        updateItemImpl.removeCategory(itemId, "milk tea");
        assertEquals(1, cItem.getCategories().size());
        assertTrue(cItem.getCategories().contains(c2));
    }

    @Test
    void testUpdateStoreImage(){
        when(findItemService.findById(itemId)).thenReturn(cItem);
        when(repo.save(cItem)).thenReturn(cItem);

        updateItemImpl.updateItemImage(itemId, "abc.com");

        assertEquals("abc.com", cItem.getImageUrl());
    }
}
