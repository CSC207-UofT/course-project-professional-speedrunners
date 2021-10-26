package test.usecase.item;

import app.adapter.db.item_db.ItemDbHashmap;
import app.adapter.presenter.GenericPresenter;
import domain.entity.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.item.RemoveItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IResponse.PresenterInterface;

import static org.junit.jupiter.api.Assertions.*;

class RemoveItemTest {
    RemoveItem removeItem;
    ItemDb repo;
    PresenterInterface presenter;

    @BeforeEach
    void setUp() {
        repo = new ItemDbHashmap();
        presenter = new GenericPresenter();
        removeItem = new RemoveItem(repo, presenter);

        //create items to store in repo
        float price = 5;
        String storeId = "abc123";
        String name = "Milk tea";
        Item item1 = new Item(price, storeId, "123", name);

        repo.add(item1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void remove() {
        RemoveItem.RemoveItemRequest request = removeItem.generateRequest("123");
        removeItem.remove(request);
        assertTrue(repo.findAll().isEmpty());
    }
}