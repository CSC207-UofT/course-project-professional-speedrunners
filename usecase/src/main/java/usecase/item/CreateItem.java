package usecase.item;

import usecase.port.ItemDb;
import usecase.port.IdGenerator;
import domain.entity.Item;

//T can be Item or RatableItem
public final class CreateItem<T extends Item> {

    private final ItemDb repo;
    private final IdGenerator idGen;

    public CreateItem(final ItemDb repo, final IdGenerator idGen){
        this.repo = repo;
        this.idGen = idGen;
    }


    //TODO: check if correct type of item is created.
    public Item create(final T item){
        Item itemToAdd = T.builder()
                .setPrice(item.getPrice())
                .setId(idGen.generate())
                .setStore(item.getStore())
                .build();

        return repo.add(itemToAdd);

    }





}
