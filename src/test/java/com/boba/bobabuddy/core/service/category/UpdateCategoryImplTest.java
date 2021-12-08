package com.boba.bobabuddy.core.service.category;

import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.data.dao.CategoryJpaRepository;
import com.boba.bobabuddy.core.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.category.impl.UpdateCategoryServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryImplTest {
    private Category category;
    private Item item1, item2;


    @Mock
    private CategoryJpaRepository repo;

    @InjectMocks
    private UpdateCategoryServiceImpl updateCategory;

    @BeforeEach
    void setUp(){
        category = new Category();
        category.setName("milk tea");

        item1 = new Item();
        item1.setCategories(Set.of(category));
        item1.setName("molk");

        item2 = new Item();
        item2.setCategories(Set.of(category));
        item2.setName("melk");

        HashSet<Item> set = new HashSet<>();
        set.add(item1);
        category.setItems(set);
    }

    @AfterEach
    void tearDown(){
        category = null;
    }

    @Test
    void testAddItem() throws DuplicateResourceException{
        when(repo.save(category)).thenReturn(category);

        Category returned = updateCategory.addItemToCategory(category, item2);
        assertEquals(2, returned.getItems().size());
        assertTrue(returned.getItems().contains(item2));
    }

    @Test
    void testRemoveItem() throws ResourceNotFoundException{
        category.addItem(item2);

        when(repo.save(category)).thenReturn(category);

        Category returnedCategory = updateCategory.removeItemFromCategory(category, item1);

        assertEquals(1, returnedCategory.getItems().size());
        assertTrue(returnedCategory.getItems().contains(item2));
        assertFalse(returnedCategory.getItems().contains(item1));

    }

}
