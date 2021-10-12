package usecase.item;

import usecase.port.ItemDb;
import domain.entity.Item;

public final class RemoveItem {

    private final ItemDb repo;

    public RemoveItem(final ItemDb repo){
        this.repo = repo;
    }

    public boolean remove(final Item item){
        return repo.remove(item);

    }





}
