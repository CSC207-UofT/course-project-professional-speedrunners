package test.domain.entity;

import domain.entity.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ItemTest {



    @Before
    public void setUp(){

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testItem() {
        float price = 5;
        String storeId = "abc123";
        String name = "Bubble tea";
        Item item = new Item(price, storeId,"123", name);

        assertEquals(5, item.getPrice(), 0);
        assertEquals("Bubble tea", item.getName());
        assertEquals("123", item.getId());
        assertEquals("abc123", item.getStoreId());
    }
}