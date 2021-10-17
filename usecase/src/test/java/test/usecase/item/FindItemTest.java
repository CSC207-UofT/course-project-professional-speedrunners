package test.usecase.item;

import app.adapter.db.item_db.ItemDbHashmap;
import app.adapter.presenter.GenericPresenter;
import org.junit.jupiter.api.AfterEach;
import usecase.item.FindItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IResponse.PresenterInterface;
import domain.entity.Item;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;


public class FindItemTest {
    FindItem findItem;
    ItemDb repo;
    PresenterInterface presenter;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @Before
    public void setUp(){
        repo = new ItemDbHashmap();
        presenter = new GenericPresenter();
        findItem = new FindItem(repo, presenter);
        System.setOut(new PrintStream(outContent));

        //create items to store in repo
        float price = 5;
        String storeId1 = "abc123";
        String storeId2 = "helpme";
        String name1 = "Milk tea";
        String name2 = "Boba";
        Item item1 = new Item(price, storeId1, "1", name1);
        Item item2 = new Item(price, storeId1, "2", name2);
        Item item3 = new Item(price, storeId2, "3", name1);
        repo.add(item1);
        repo.add(item2);
        repo.add(item3);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    //TODO: the newline character that gets generated from java are different between macos and windows.
    // \r\n works for windows and \r works for mac.
    @Test
    public void testFindByStore() {
        FindItem.FindItemRequest itemRequest = new FindItem.FindItemRequest();
        itemRequest.buildStoreId("abc123");
        findItem.findByStore(itemRequest);
        String expected = "Operation findByStore Success!\r\n[Item: Milk tea, Price: 5.0, Id: 1, Item: Boba, " +
                "Price: 5.0, Id: 2]\r\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void testFindById() {
        FindItem.FindItemRequest itemRequest = new FindItem.FindItemRequest();
        itemRequest.buildItemId("1");
        findItem.findById(itemRequest);
        String expected = "Operation findById Success!\r\n[Item: Milk tea, Price: 5.0, Id: 1]\r\n";
        assertEquals(expected, outContent.toString());
    }

    //without sorting
    @Test
    public void testFindAll() {
        FindItem.FindItemRequest itemRequest = new FindItem.FindItemRequest();
        itemRequest.buildSorted("p");
        findItem.findAll(itemRequest);
        String expected = "Operation findAll Success!\r\n[Item: Milk tea, Price: 5.0, Id: 1, " +
                "Item: Boba, Price: 5.0, Id: 2, " +
                "Item: Milk tea, Price: 5.0, Id: 3]\r\n";
        assertEquals(expected, outContent.toString());
    }
}
