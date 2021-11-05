package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.itemport.IFindItem;
import com.boba.bobabuddy.core.usecase.port.storeport.IFindStore;
import com.boba.bobabuddy.infrastructure.database.StoreJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
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
    private final IFindItem findItem;

    /**
     * Initalize FindStore usecase by injecting dependencies
     *
     * @param repo     database object for handling item data
     * @param findItem
     */
    // Spring annotation that instruct springboot to attempt to automatically inject dependencies as needed.
    @Autowired
    public FindStore(final StoreJpaRepository repo, IFindItem findItem) {
        this.repo = repo;
        this.findItem = findItem;
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
    public List<Store> findByLocation(String location) {
        return repo.findByLocation(location);
    }

    /***
     * Find Store by its uuid
     * @param id uuid of the store
     * @return the Store if it was found
     * @throws ResourceNotFoundException if this store does not exist
     */
    @Override
    public Store findById(UUID id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such store", new Exception()));
    }

    /***
     * Find Store by its name. Have to be fully matching.
     * @param name name to be matched
     * @return Store that has name as its name. Or null if no such Store exist
     */
    @Override
    public List<Store> findByName(String name) {
        return repo.findByName(name);
    }

    /***
     * Find Store by its name. Also do partial match.
     * @param name name to be matched
     * @return Store that has a name containing param name, or an empty list if no such Store exist
     */
    @Override
    public List<Store> findByNameContaining(String name) {
        return repo.findByNameContaining(name);
    }

    /***
     * Find all stores that have avgRating greater than or equal to param rating
     * @param avgRating avgRating to be compared with
     * @return Stores that has avgRating greater than or equal to param rating, or an empty list if no such Store exist
     */
    @Override
    public List<Store> findByAvgRatingGreaterThanEqual(float avgRating, boolean sorted) {
        if (0 <= avgRating && avgRating <= 1) {
            Sort sorter = ((sorted) ? Sort.by("avgRating").descending() : Sort.unsorted());
            return repo.findByAvgRatingGreaterThanEqual(avgRating, sorter);
        }
        throw new IllegalArgumentException("Rating out of bounds");
    }

    @Override
    public Store findByItem(UUID id) throws ResourceNotFoundException {
        return findItem.findById(id).getStore();
    }

    @Override
    public Store findByRating(UUID id) throws ResourceNotFoundException {
        return repo.findByRatings_id(id).orElseThrow(() -> new ResourceNotFoundException("No Store with the specified rating exist"));
    }
}

