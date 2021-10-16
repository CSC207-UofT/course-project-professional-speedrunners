package usecase.port.IDb;

import domain.entity.Item;
import domain.entity.Store;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Interface for item database
 * The following method should be supported by a database implementation for item data
 */
public interface ItemDb {

    Item setPrice(float price, Item item);

    boolean add(Item item);

    Optional<Item> remove(String itemId);

    Optional<Item> findById(String id);

    List<Item> findByStore(String storeId);

    List<Item> findAll();
}
