package usecase.store;

import domain.entity.Store;
import usecase.port.IDb.StoreDb;

public final class RemoveStore {

    private final StoreDb storeDb;

    public RemoveStore(final StoreDb storeDb) {
        this.storeDb = storeDb;
    }

    public boolean remove(final Store store) {
        return storeDb.remove(store);
    }

}
