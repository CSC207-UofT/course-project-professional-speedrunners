package usecase.store;

import domain.entity.Item;
import domain.entity.Store;
import usecase.port.IDb.StoreDb;

import java.util.Optional;

public final class FindStore {

    private final StoreDb storeDb;

    public FindStore(StoreDb storeDb) {
        this.storeDb = storeDb;
    }

    public Optional<Store> findById(final String storeId) {
        return storeDb.findById(storeId);

    }

    public Optional<Store> findByLocation(final String location) {
        return storeDb.findByLocation(location);
    }

    public Optional<Store> findByItem(final Item item) {
        //TODO: what to do whit this one, and if db access is needed
        return null;
    }


}
