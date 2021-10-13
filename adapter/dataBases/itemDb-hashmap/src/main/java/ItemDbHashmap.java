import domain.entity.Item;
import domain.entity.Store;
import usecase.port.ItemDb;

import java.util.*;
import java.util.stream.Collectors;

public class ItemDbHashmap implements ItemDb {

    private final Map<String, Item> inMemoryDb = new HashMap<>();


    @Override
    public Item setPrice(float price, Item item) {
        if(inMemoryDb.containsKey(item.getId())){
            inMemoryDb.get(item.getId()).setPrice(price);
        }
        return item;
    }

    @Override
    public boolean add(final Item item) {
        inMemoryDb.put(item.getId(), item);
        return item;
    }

    //TODO: might need to rethink this api
    @Override
    public boolean remove(Item item) {
        return inMemoryDb.remove(item.getId(), item);
    }

    @Override
    public Optional<Item> findById(String id) {
        return Optional.ofNullable(inMemoryDb.get(id));
    }

    @Override
    public List<Item> findByStore(Store store) {
        return inMemoryDb.values().stream()
                .filter(item -> item.getStore().equals(store))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> findAll() {
        return (ArrayList<Item>) inMemoryDb.values();
    }
}
