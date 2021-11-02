package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.port.storeport.IFindStore;
import com.boba.bobabuddy.core.usecase.store.exceptions.StoreNotFoundException;
import com.boba.bobabuddy.infrastructure.database.StoreJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * This class handle the usecase of finding stores in the system.
 * It implements the IFindStore interface which defines what operations are supported by the usecase object
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
public class FindStore implements IFindStore {
    //JPA repository port - Handles queries and update, creation, deletion of entries in the database
    private final StoreJpaRepository repo;

    /**
     * Initalize FindStore usecase by injecting dependencies
     *
     * @param repo database object for handling item data
     */
    // Spring annotation that instruct springboot to attempt to automatically inject dependencies as needed.
    @Autowired
    public FindStore(final StoreJpaRepository repo) {
        this.repo = repo;
    }

    //usecase interactors

    /***
     * Find all stores that exist in the database
     * @return list of all stores in the database, or an empty list if the database is empty
     */
    @Override
    public List<Store> findAll() {
        return repo.findAll();
    }

    /***
     * Find Store by its location
     * @param location location of the store
     * @return the Store if it was found. Otherwise, return null.
     */
    @Override
    public Store findByLocation(String location) {
        return repo.findByLocation(location);
    }

    /***
     * Find Store by its uuid
     * @param id uuid of the store
     * @return the Store if it was found
     * @throws StoreNotFoundException if this store does not exist
     */
    @Override
    public Store findById(UUID id) throws StoreNotFoundException {
        var store = repo.findById(id);
        if (store.isPresent()) return store.get();
        throw new StoreNotFoundException();
    }

    /***
     * Find Store by its name. Have to be fully matching.
     * @param name name to be matched
     * @return Store that has name as its name. Or null if no such Store exist
     */
    @Override
    public Store findByName(String name) {
        return repo.findByName(name);
    }

    /***
     * Find all stores that have avgRating greater than or equal to param rating
     * @param avgRating avgRating to be compared with
     * @return Stores that has avgRating greater than or equal to param rating, or an empty list if no such Store exist
     */
    @Override
    public List<Store> findByAvgRatingGreaterThanEqual(float avgRating){
        if (0.0 <= avgRating && avgRating <= 1){
            return repo.findByAvgRatingGreaterThanEqual(avgRating);
        }
        throw new IllegalArgumentException("Rating out of bounds");
    }
}

