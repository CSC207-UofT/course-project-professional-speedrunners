package com.boba.bobabuddy.core.usecase.item;


import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.item.port.IFindItem;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * This class handle the usecase of finding items in the system.
 */

@Service
@Transactional
public class FindItem implements IFindItem {

    private final ItemJpaRepository repo;

    /**
     * Initialize FindItem usecase by injecting dependencies
     *
     * @param repo data access object for handling item data
     */
    public FindItem(final ItemJpaRepository repo) {
        this.repo = repo;
    }

    /***
     * Find an Item by its Store.
     * The query matches the uuid of the store.
     * @param id uuid of the Store object
     * @return A list of Item that belongs to the store. Or an empty list if no Item is sold from this store.
     */
    @Override
    public List<Item> findByStore(UUID id) {
        return repo.findByStore_id(id);
    }

    /***
     * Find Item by its uuid
     * @param id uuid of the item
     * @return the Item if it was found.
     * @throws ResourceNotFoundException Thrown when no item with matching ID exist in the database
     */
    @Override
    public Item findById(UUID id) throws ResourceNotFoundException {
        var item = repo.findById(id);
        if (item.isPresent()) return item.get();
        throw new ResourceNotFoundException("No such Item", new Exception());
    }

    /***
     * Find all items that exist in the database
     * TODO: review this code
     *  probably not a good idea if the database become large. Consider adding a pageable alternative to this
     *  and not expose this directly to the api.
     *  Refer to Spring Data JPA for detail
     * @return list of all items in the database, or an empty list if the database is empty
     */
    @Override
    public List<Item> findAll() {
        return repo.findAll();
    }

    /***
     * Find Item by its name. Have to be fully matching.
     * @param name name to be matched
     * @return Item with matching name. Or an empty list if no such Item exist
     */
    @Override
    public List<Item> findByName(String name) {
        return repo.findByName(name);
    }

    /***
     * Find Item by its name. Also do partial match.
     * @param name name to be matched
     * @return Item that partially matches the name, or an empty list if no such Item exist
     */
    @Override
    public List<Item> findByNameContaining(String name) {
        return repo.findByNameContaining(name);
    }

    /***
     * Find all items that have price less than or equal to param price
     * @param price price to be compared with
     * @param sorted give an ascending sorted list return if sorted is true
     * @return Items that have price less than or equal to param price, or an empty list if no such Item exist
     */
    @Override
    public List<Item> findByPriceLessThanEqual(float price, boolean sorted) {
        Sort sorter = ((sorted) ? Sort.by("price").ascending() : Sort.unsorted());
        return repo.findByPriceLessThanEqual(price, sorter);
    }

    /***
     * Find all items that have avgRating greater than or equal to param rating
     * @param avgRating avgRating to be compared with
     * @return Items that has avgRating greater than or equal to param rating, or an empty list if no such Item exist
     * @throws IllegalArgumentException when avgRating is out of bound
     */
    @Override
    public List<Item> findByAvgRatingGreaterThanEqual(float avgRating, boolean sorted) throws IllegalArgumentException {
        if (avgRating > 1 || avgRating < 0) throw new IllegalArgumentException("avgRating must be between 0 and 1");
        Sort sorter = ((sorted) ? Sort.by("avgRating").descending() : Sort.unsorted());
        return repo.findByAvgRatingGreaterThanEqual(avgRating, sorter);
    }

    /***
     * Find item that has a matching rating object
     * @param id id of the Rating entity
     * @return Item if such item exist
     * @throws ResourceNotFoundException thrown when no Item possessing this rating object exist
     */
    @Override
    public Item findByRating(UUID id) throws ResourceNotFoundException {
        return repo.findByRatings_id(id).orElseThrow(() -> new ResourceNotFoundException("No Item with the specified rating exist"));
    }


}

