
package test.usecase.item;

import app.adapter.id_generator.uuid.UuidGen;
import app.adapter.db.item_db.ItemDbHashmap;
import app.adapter.presenter.GenericPresenter;
import usecase.item.CreateItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IdGenerator;
import usecase.port.IResponse.PresenterInterface;
import domain.entity.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CreateItemTest {
    ItemDb repo;
    IdGenerator id;
    PresenterInterface presenter;
    CreateItem createItem;


    @Before
    public void setUp(){
        repo = new ItemDbHashmap();
        id = new UuidGen();
        presenter = new GenericPresenter();
        createItem = new CreateItem(repo, id, presenter);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreate() {
        float price = 5;
        String storeId = "abc123";
        String name = "Bubble tea";
        createItem.create(createItem.generateRequest(price, storeId, name));
        List<Item> result = repo.findAll();

        assertFalse(result.isEmpty());
        assertEquals("Bubble tea", (result.get(0)).getName());
        assertEquals(5, (result.get(0)).getPrice(), 0);
    }
}