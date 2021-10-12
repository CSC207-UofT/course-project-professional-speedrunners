package usecase.item;

import domain.entity.Item;
import domain.entity.RatableItem;
import usecase.port.ItemDb;

import java.util.List;

public final class SortItem {

    private ItemDb db;

    public SortItem(final ItemDb db){
        this.db = db;
    }

    public List<Item> sortByPrice(){
        return db.sortByPrice();
    }

    public List<RatableItem> sortByRating(){
        return db.sortByRating();
    }


}
