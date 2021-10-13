package usecase.item;

import domain.entity.Item;
import domain.entity.Store;
import usecase.port.IItemIn.IFindItem;
import usecase.port.IStoreIn.IFindStore;
import usecase.port.ItemDb;
import usecase.port.RequestModel;
import usecase.port.RespondModel;

import java.util.List;
import java.util.Optional;

public final class FindItem implements IFindItem {

    private final ItemDb repo;

    public FindItem(final ItemDb repo) {
        this.repo = repo;
    }

    public FindItemRequest requestGenerator(){

    }

    public FindItemResponse findByStore(final Store store) {

        return repo.findByStore(store);
    }

    public Optional<Item> findById(final String id) {
        return repo.findById(id);

    }

    @Override
    public FindItemResponse findByStore(FindItemRequest store) {
        return null;
    }

    @Override
    public FindItemResponse findById(FindItemRequest id) {
        return null;
    }

    public List<Item> findAll() {
        return repo.findAll();
    }

    @Override
    public FindItemRequest requestFindByStore(String storeId) {
        return null;
    }

    @Override
    public FindItemRequest requestFindById(String itemId) {
        return null;
    }


    public static class FindItemResponse implements RespondModel{}
    public static class FindItemRequest implements RequestModel{
        private String storeId;
        private String itemId;

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }
        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStoreId() {
            return storeId;
        }

        public String getItemId() {
            return itemId;
        }
    }

    }

}
