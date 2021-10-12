package usecase.item;

import domain.entity.Item;
import usecase.port.ItemDb;

public final class SetPrice {

    private final ItemDb db;

    public SetPrice(final ItemDb db){
        this.db = db;
    }

    public Item set(float price, Item item){
        return db.setPrice(price, item);
    }

}
