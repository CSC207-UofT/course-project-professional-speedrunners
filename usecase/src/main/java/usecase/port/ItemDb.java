package usecase.port;

import domain.entity.Item;
import domain.entity.RatableItem;
import domain.entity.Store;

import java.util.List;
import java.util.Optional;

public interface ItemDb {

    Item setPrice(float price, Item item);

    Item add(Item item);

    boolean remove(Item item);

    Optional<Item> findById(String id);

    List<Item> findByStore(Store store);

    List<Item> findAll();
}
