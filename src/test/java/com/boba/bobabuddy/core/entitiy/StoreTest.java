package com.boba.bobabuddy.core.entitiy;

import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Store;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoreTest {
    private Store store;
    private Item item;

    private UUID storeId;

    @BeforeEach
    void setup(){
        storeId = UUID.randomUUID();
        store = new Store();
        store.setId(storeId);
        store.setName("M.Jordan's fav milk tea");
        store.setLocation("75 Charles St, Toronto, Ontario M5S 1K9");
        store.setOwner("Bob");

        item = new Item();
        item.setStore(store);
    }

    @AfterEach
    void tearDown(){
        store = null;
        item = null;
    }

    @Test
    void testGetMenu(){
        store.addItem(item);
        assertEquals(1, store.getMenu().size());
    }

    @Test
    void testGetLocation(){
        assertEquals("75 Charles St, Toronto, Ontario M5S 1K9", store.getLocation());
    }

    @Test
    void testSetLocation(){
        store.setLocation("Mars");
        assertEquals("Mars", store.getLocation());
    }

    @Test
    void testAddItem(){
        Item item1 = new Item();
        item1.setId(UUID.randomUUID());
        Item item2 = new Item();
        item2.setId(UUID.randomUUID());

        store.addItem(item1);
        store.addItem(item2);
        assertTrue(store.getMenu().contains(item1));
        assertTrue(store.getMenu().contains(item2));
    }

    @Test
    void testRemoveItem(){
        store.removeItem(item);
        assertEquals(0, store.getMenu().size());
    }


}
