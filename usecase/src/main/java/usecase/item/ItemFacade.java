package usecase.item;

import domain.entity.Item;
import domain.entity.Store;
import usecase.port.IdGenerator;
import usecase.port.ItemDb;

import java.util.List;
import java.util.Optional;

public class ItemFacade {


    public static final class RemoveItem {

        private final ItemDb repo;

        public RemoveItem(final ItemDb repo) {
            this.repo = repo;
        }

        public boolean remove(final Item item) {
            return repo.remove(item);

        }


    }

    public static class UpdateItem {
        private final ItemDb db;

        public UpdateItem(final ItemDb db) {
            this.db = db;
        }

        public Item setPrice(final float price, final Item item) {
            return db.setPrice(price, item);
        }

    }

    public static final class FindItem {

        private final ItemDb repo;

        public FindItem(final ItemDb repo) {
            this.repo = repo;
        }

        public List<Item> findByStore(final Store store) {
            return repo.findByStore(store);
        }

        public Optional<Item> findById(final String id) {
            return repo.findById(id);

        }

        public List<Item> findAll() {
            return repo.findAll();
        }


    }

    public static final class CreateItem {

        private final ItemDb repo;
        private final IdGenerator idGen;

        public CreateItem(final ItemDb repo, final IdGenerator idGen) {
            this.repo = repo;
            this.idGen = idGen;
        }


        //TODO: how to create Ratable item
        public Item create(final Item item) {
            Item itemToAdd = Item.builder()
                    .setPrice(item.getPrice())
                    .setId(idGen.generate())
                    .setStore(item.getStore())
                    .build();

            return repo.add(itemToAdd);

        }


    }
}
