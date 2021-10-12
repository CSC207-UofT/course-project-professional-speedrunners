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

    List<Item> sortByPrice();

    List<RatableItem> sortByRating();

    Optional<Item> findById(String id);

    Optional<List<Item>> findByStore(Store store);

    List<Item> findAll();
}
