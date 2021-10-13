package app.adapter.db.item_db;

import domain.entity.Item;
import usecase.port.IDb.ItemDb;

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
        if(inMemoryDb.containsKey(item.getId())){
            return false;
        }
        inMemoryDb.put(item.getId(), item);
        return true;
    }


    //TODO: might need to rethink this api
    public Optional<Item> remove(String itemId) {
        return Optional.ofNullable(inMemoryDb.remove(itemId));
    }

    @Override
    public Optional<Item> findById(String id) {
        return Optional.ofNullable(inMemoryDb.get(id));
    }

    @Override
    public List<Item> findByStore(String storeId) {
        return inMemoryDb.values().stream()
                .filter(item -> item.getStoreId().equals(storeId))
                .collect(Collectors.toList());
    }
    @Override
    public List<Item> findAll() {
        return new ArrayList<>(inMemoryDb.values());
    }
}
