package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.item.port.IFindItem;
import com.boba.bobabuddy.core.usecase.store.port.IFindStore;
import com.boba.bobabuddy.infrastructure.dao.StoreJpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * This class handle the usecase of finding stores in the system.
 */
@Service
@Transactional
public class FindStore implements IFindStore {
    private final StoreJpaRepository repo;
    private final IFindItem findItem;

    /**
     * Initialize FindStore usecase by injecting dependencies
     *
     * @param repo     database object for handling item data
     * @param findItem FindItem usecase
     */
    public FindStore(final StoreJpaRepository repo, IFindItem findItem) {
        this.repo = repo;
        this.findItem = findItem;
    }

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
     * @return Store with matching name. Or an empty list if no such Store exist
     */
    @Override
    public List<Store> findByName(String name) {
        return repo.findByName(name);
    }

    /***
     * Find Store by its name. Also do partial match.
     * @param name name to be matched
     * @return Store that partially matches the name, or an empty list if no such Store exist
     */
    @Override
    public List<Store> findByNameContaining(String name) {
        return repo.findByNameContaining(name);
    }

    /***
     * Find all Store that have avgRating greater than or equal to param rating
     * @param avgRating avgRating to be compared with
     * @return Store that has avgRating greater than or equal to param rating, or an empty list if no such Store exist
     * @throws IllegalArgumentException when avgRating is out of bound
     */
    @Override
    public List<Store> findByAvgRatingGreaterThanEqual(float avgRating, boolean sorted) {
        if (0 <= avgRating && avgRating <= 1) {
            Sort sorter = ((sorted) ? Sort.by("avgRating").descending() : Sort.unsorted());
            return repo.findByAvgRatingGreaterThanEqual(avgRating, sorter);
        }
        throw new IllegalArgumentException("avgRating must be between 0 and 1");
    }

    /***
     * Find store that contains the specified Item
     * @param id id of the item entity
     * @return the store satisfying the condition
     * @throws ResourceNotFoundException thrown when no such store exist
     */
    @Override
    public Store findByItem(UUID id) throws ResourceNotFoundException {
        return findItem.findById(id).getStore();
    }

    /***
     * Find store that contains the specified Rating
     * @param id id of the Rating entity
     * @return the store satisfying the condition
     * @throws ResourceNotFoundException thrown when no such store exist
     */
    @Override
    public Store findByRating(UUID id) throws ResourceNotFoundException {
        return repo.findByRatings_id(id).orElseThrow(() -> new ResourceNotFoundException("No Store with the specified rating exist"));
    }
}

