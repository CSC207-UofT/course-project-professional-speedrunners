package usecase.store;

import domain.entity.Store;
import usecase.port.IdGenerator;
import usecase.port.StoreDb;

public final class CreateStore {

    private StoreDb storeDb;
    private IdGenerator idGen;

    public CreateStore(final StoreDb storeDb, final IdGenerator idGen) {
        this.storeDb = storeDb;
        this.idGen = idGen;
    }

    public Store create(final Store store) {
        Store storeToAdd = Store.builder()
                .setLocation(store.getLocation())
                .setMenu(store.getMenu())
                .setName(store.getName())
                .setId(idGen.generate())
                .build();

        return storeDb.add(storeToAdd);

    }

}
