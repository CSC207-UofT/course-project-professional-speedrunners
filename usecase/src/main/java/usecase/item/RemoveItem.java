package usecase.item;

import domain.entity.Item;
import usecase.port.IItemIn.IRemoveItem;
import usecase.port.ItemDb;
import usecase.port.RespondModel;

public final class RemoveItem implements IRemoveItem {

    private final ItemDb repo;

    public RemoveItem(final ItemDb repo) {
        this.repo = repo;
    }

    public boolean remove(final Item item) {
        return repo.remove(item);

    }

    public static class RemoveItemResponse implements RespondModel {}

}
