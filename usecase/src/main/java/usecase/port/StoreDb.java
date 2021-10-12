package usecase.port;

import domain.entity.Store;

import java.util.Optional;

public interface StoreDb {
    Store add(Store storeToAdd);

    boolean remove(Store store);

    Optional<Store> findByLocation(String location);

    Optional<Store> findById(String storeId);
}
