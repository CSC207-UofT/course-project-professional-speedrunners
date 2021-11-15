package com.boba.bobabuddy.core.entitiy;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.entity.Item;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {

    @Test
    void testGetMenu(){
        Store store = new Store("M.Jordan's fav milk tea", "75 Charles St, Toronto, Ontario M5S 1K9");
        Item item = new Item(5, store, "milk tea");
        store.addItem(item);
        assertEquals(1, store.getMenu().size());
    }

    @Test
    void testGetLocation(){
        Store store1 = new Store("M.Jordan's fav milk tea", "75 Charles St, Toronto, Ontario M5S 1K9");
        assertEquals("75 Charles St, Toronto, Ontario M5S 1K9", store1.getLocation());
    }

    @Test
    void testSetLocation(){
        Store store1 = new Store("M.Jordan's fav milk tea", "75 Charles St, Toronto, Ontario M5S 1K9");
        store1.setLocation("Mars");
        assertEquals("Mars", store1.getLocation());
    }

    @Test
    void testAddItem(){
        Store store1 = new Store("M.Jordan's fav milk tea", "75 Charles St, Toronto, Ontario M5S 1K9");
        Item item1 = new Item(5, store1, "milk tea");
        Item item2 = new Item(19, store1, "coconut milk tea");
        store1.addItem(item1);
        store1.addItem(item2);
        assertTrue(store1.getMenu().contains(item1));
        assertTrue(store1.getMenu().contains(item2));
    }

    @Test
    void testRemoveItem(){
        Store store1 = new Store("M.Jordan's fav milk tea", "75 Charles St, Toronto, Ontario M5S 1K9");
        Item item1 = new Item(5, store1, "milk tea");
        Item item2 = new Item(19, store1, "coconut milk tea");
        store1.addItem(item1);
        store1.addItem(item2);
        store1.removeItem(item2);
        assertTrue(store1.getMenu().contains(item1));
        assertFalse(store1.getMenu().contains(item2));
    }


}
