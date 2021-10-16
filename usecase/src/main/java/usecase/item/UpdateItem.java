package usecase.item;

import domain.entity.Item;
import usecase.port.IRequest.IItemIn.IUpdateItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IResponse.RespondModel;

import java.util.List;
/**
 * This class handle the usecase of updating items in the system.
 * It implements the IFindItem interface which defines what operations are supported by the usecase object
 * from a controller's perspective.
 */

// Not used as of phase 0
public final class UpdateItem implements IUpdateItem {
    private final ItemDb db;

    public UpdateItem(final ItemDb db) {
        this.db = db;
    }

    public Item setPrice(final float price, final Item item) {
        return db.setPrice(price, item);
    }

    @Override
    public RespondModel execute() {
        return null;
    }

    public static class UpdateItemResponse implements RespondModel {

        @Override
        public boolean getResult() {
            return false;
        }

        @Override
        public List<String> getResponse() {
            return null;
        }

        @Override
        public String getOperation() {
            return null;
        }
    }

}
