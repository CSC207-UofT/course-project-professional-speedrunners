package usecase.item;

import domain.entity.Item;
import usecase.port.IItemIn.IUpdateItem;
import usecase.port.ItemDb;
import usecase.port.RespondModel;

public final class UpdateItem implements IUpdateItem {
    private final ItemDb db;

    public UpdateItem(final ItemDb db) {
        this.db = db;
    }

    public Item setPrice(final float price, final Item item) {
        return db.setPrice(price, item);
    }

    public static class UpdateItemResponse implements RespondModel {

    }

}
