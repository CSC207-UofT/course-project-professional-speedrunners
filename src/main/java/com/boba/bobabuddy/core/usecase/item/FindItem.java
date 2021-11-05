package com.boba.bobabuddy.core.usecase.item;


import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.itemport.IFindItem;
import com.boba.bobabuddy.core.usecase.port.storeport.IFindStore;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * This class handle the usecase of finding items in the system.
 * It implements the IFindItem interface which defines what operations are supported by the usecase object
 * from a controller's perspective.
 */
// Springframework annotations that marks this class as a service component
// Functionally identical to the @Component annotation as far as I'm aware, and it essentially
// registers the class as a component (bean) so that Spring can automatically configure and inject dependencies
// as needed.
@Service
// Indicates that operations performed in this class in Transactional.
// Refers to this link for more info: https://java.christmas/2019/24
@Transactional
public class FindItem implements IFindItem {

    /***
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final ItemJpaRepository repo;
    private final IFindStore findStore;

    /**
     * Initalize FindItem usecase by injecting dependencies
     *
     * @param repo database object for handling item data
     * @param findStore
     */
    // Spring annotation that instruct springboot to attempt to automatically inject dependencies as needed.
    @Autowired
    public FindItem(final ItemJpaRepository repo, IFindStore findStore) {
        this.repo = repo;
        this.findStore = findStore;
    }


    //usecase interactors

    /***
     * Find an Item by its Store.
     * The query matches the uuid of the store.
     * TODO: Review this code
     *     This could be performed in a much simpler query by finding the store object and
     *     returning its menu
     *     Replace this method once other Repositories are implemented.
     * @param id uuid of the Store object
     * @return A list of Item that belongs to the store. Or an empty list if no Item is sold from this store.
     */
    @Override
    public List<Item> findByStore(UUID id) {
        return repo.findByStore_id(id);
    }

    /***
     * Find Item by its uuid
     * TODO: add exception
     *     while the repository query can returns an optional, users should be notified if a match was not found.
     *     Create an custom exception in the usecase layer and have this method handle the null return instead of
     *     giving it back to the controller.
     * @param id uuid of the item
     * @return the Item if it was found. Otherwise, return null.
     */
    @Override
    public Item findById(UUID id) throws ResourceNotFoundException {
        var item = repo.findById(id);
        if (item.isPresent()) return item.get();
        throw new ResourceNotFoundException("not found", new Exception());
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
     * @return Item that has name as its name. Or an empty list if no such Item exist
     */
    @Override
    public List<Item> findByName(String name) {
        return repo.findByName(name);
    }

    /***
     * Find Item by its name. Also do partial match.
     * @param name name to be matched
     * @return Item that has a name containing param name, or an empty list if no such Item exist
     */
    @Override
    public List<Item> findByNameContaining(String name) {
        return repo.findByNameContaining(name);
    }

    /***
     * Find all items that have price less than or equal to param price
     * @param price price to be compared with
     * @return Items that have price less than or equal to param price, or an empty list if no such Item exist
     */
    @Override
    public List<Item> findByPriceLessThanEqual(float price, boolean sorted) {
        Sort sorter = ((sorted) ? Sort.by("price").ascending() : Sort.unsorted());
        return repo.findByPriceLessThanEqual(price, sorter);
    }

    /***
     * Find all items that have avgRating greater than or equal to param rating
     * TODO: request with a avgRating that is not between 0 and 1 should result in an exception
     * @param avgRating avgRating to be compared with
     * @return Items that has avgRating greater than or equal to param rating, or an empty list if no such Item exist
     */
    @Override
    public List<Item> findByAvgRatingGreaterThanEqual(float avgRating, boolean sorted) {
        Sort sorter = ((sorted) ? Sort.by("avgRating").descending() : Sort.unsorted());
        return repo.findByAvgRatingGreaterThanEqual(avgRating, sorter);
    }


}

