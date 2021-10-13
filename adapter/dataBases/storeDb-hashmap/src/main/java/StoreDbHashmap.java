import domain.entity.Store;
import usecase.port.IDb.StoreDb;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StoreDbHashmap implements StoreDb {

    private final Map<String, Store> inMemoryDb = new HashMap<>();

    @Override
    public Store add(domain.entity.Store storeToAdd) {
        inMemoryDb.put(storeToAdd.getId(), storeToAdd);
        return storeToAdd;
    }

    @Override
    public boolean remove(Store store) {
        return inMemoryDb.remove(store.getId(), store);
    }

    @Override
    public Optional<Store> findByLocation(String location) {
        return inMemoryDb.values().stream()
                .filter(store -> store.getLocation().equals(location))
                .findAny();
    }

    @Override
    public Optional<Store> findById(String storeId) {
        return Optional.ofNullable(inMemoryDb.get(storeId));
    }
}
