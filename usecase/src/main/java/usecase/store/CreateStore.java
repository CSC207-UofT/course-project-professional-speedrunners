package usecase.store;

import domain.entity.Store;
import usecase.port.StoreDb;

public final class CreateStore {

    private StoreDb storeDb;

    public CreateStore(StoreDb storeDb){
        this.storeDb = storeDb;
    }

    public Store create(final Store store){
        Store storeToAdd = Store.builder()
                .setLocation(store.getLocation())
                .setMenu(store.getMenu())
                .setName(store.getName())
                .build();

        return storeDb.add(storeToAdd);

    }

}
