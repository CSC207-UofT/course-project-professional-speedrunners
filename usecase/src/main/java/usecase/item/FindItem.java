package usecase.item;

import domain.entity.Store;
import usecase.port.ItemDb;
import domain.entity.Item;

import java.util.List;
import java.util.Optional;

public final class FindItem{

    private final ItemDb repo;

    public FindItem(final ItemDb repo){
        this.repo = repo;
    }

    public Optional<List<Item>> findByStore(final Store store){
        return repo.findByStore(store);
    }

    public Optional<Item> findById(final String id) {
        return repo.findById(id);

    }

    public List<Item> findAll(){
        return repo.findAll();
    }





}
