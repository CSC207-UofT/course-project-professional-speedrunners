package com.boba.bobabuddy.core.entitiy;

import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Store;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {
    private Store store;
    private Item item;

    private UUID itemId;

    @BeforeEach
    void setup(){
        itemId = UUID.randomUUID();

        store = new Store();
        store.setName("M.Jordan's fav milk tea");
        store.setLocation("75 Charles St, Toronto, Ontario M5S 1K9");
        store.setOwner("Bob");

        item = new Item();
        item.setStore(store);
        item.setPrice(5);
        Set<Category> setCat = new HashSet<>();
        item.setCategories(setCat);
    }

    @AfterEach
    void tearDown(){
        store = null;
        item = null;
    }

    @Test
    void testGetStore(){
        assertEquals(store, item.getStore());
    }

    @Test
    void testAddCategory(){
        Category category1 = new Category();
        category1.setId(UUID.randomUUID());
        category1.setName("bb");

        Category category2 = new Category();
        category2.setId(UUID.randomUUID());
        category2.setName("cc");

        Set<Category> catSet = new HashSet<>();
        catSet.add(category1);
        catSet.add(category2);

        item.addCategory(category1);
        item.addCategory(category2);

        assertEquals(catSet, item.getCategories());
    }

    @Test
    void testRemoveCategory(){
        Category category1 = new Category();
        category1.setId(UUID.randomUUID());
        category1.setName("bb");

        Category category2 = new Category();
        category2.setId(UUID.randomUUID());
        category2.setName("cc");

        Set<Category> catSet = new HashSet<>();
        catSet.add(category1);

        item.addCategory(category1);
        item.addCategory(category2);

        item.removeCategory(category2);

        assertEquals(catSet, item.getCategories());
    }
}
