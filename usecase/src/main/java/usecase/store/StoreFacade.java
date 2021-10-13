package usecase.store;

import domain.entity.Item;
import domain.entity.Store;
import usecase.port.IdGenerator;
import usecase.port.StoreDb;

import java.util.Optional;

public class StoreFacade {
    public static final class UpdateStore {
    }

    public static final class RemoveStore {

        private final StoreDb storeDb;

        public RemoveStore(final StoreDb storeDb){
            this.storeDb = storeDb;
        }

        public boolean remove(final Store store){
            return storeDb.remove(store);
        }

    }

    public static final class FindStore {

        private final StoreDb storeDb;

        public FindStore(StoreDb storeDb){
            this.storeDb = storeDb;
        }

        public Optional<Store> findById(final String storeId){
            return storeDb.findById(storeId);

        }

        public Optional<Store> findByLocation(final String location){
            return storeDb.findByLocation(location);
        }

        public Optional<Store> findByItem(final Item item){
            //TODO: what to do whit this one, and if db access is needed
            return null;
        }


    }

    public static final class CreateStore {

        private StoreDb storeDb;
        private IdGenerator idGen;

        public CreateStore(final StoreDb storeDb, final IdGenerator idGen){
            this.storeDb = storeDb;
            this.idGen = idGen;
        }

        public Store create(final Store store){
            Store storeToAdd = Store.builder()
                    .setLocation(store.getLocation())
                    .setMenu(store.getMenu())
                    .setName(store.getName())
                    .setId(idGen.generate())
                    .build();

            return storeDb.add(storeToAdd);

        }

    }
}
