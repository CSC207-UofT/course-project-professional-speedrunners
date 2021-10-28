package com.boba.bobabuddy.core.usecase.item;



import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.port.IRequest.IItemIn.IFindItem;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class handle the usecase of finding items in the system.
 * It implements the IFindItem interface which defines what operations are supported by the usecase object
 * from a controller's perspective.
 */
@Service
@Transactional
public class FindItem implements IFindItem {

    private final ItemJpaRepository repo;

    /**
     * Initalize FindItem usecase by injecting dependencies
     * @param repo database object for handling item data
     * @param presenter a presenter object responsible for translating data from usecase readable format into view
     *                  object readable format (view model).
     */
    @Autowired
    public FindItem(final ItemJpaRepository repo) {
        this.repo = repo;
    }


    //usecase interactors
    /**
     * a usecase interactor.
     * the method searches the database and find items that belong to the specified store.
     * note that usecase methods passes the response directly to the presenter within the method instead of
     * generating a return type.
     */
    @Override
    public List<Item> findByStore(UUID id) {
        return repo.findByStore_id(id);

    }

    @Override
    public Optional<Item> findById(UUID id) {
        return repo.findById(id);
    }

    @Override
    public List<Item> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Item> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public List<Item> findByNameContaining(String name) {
        return repo.findByNameContaining(name);
    }

    @Override
    public List<Item> findByPriceLessThanEqual(float price) {
        return repo.findByPriceLessThanEqual(price);
    }

    @Override
    public List<Item> findByAvgRatingGreaterThanEqual(float rating) {
       return repo.findByAvgRatingGreaterThanEqual(rating);
    }


}

