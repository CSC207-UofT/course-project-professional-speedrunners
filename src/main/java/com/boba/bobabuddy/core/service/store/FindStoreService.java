package com.boba.bobabuddy.core.service.store;

import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface FindStoreService {
    /**
     * Find all stores that exist in the database
     *
     * @return list of all stores in the database, or an empty list if the database is empty
     */
    List<Store> findAll();

    /**
     * Find Store by its location
     *
     * @param location location of the store
     * @return the Store if it was found. Otherwise, return null.
     */
    List<Store> findByLocation(String location);

    /**
     * Find Store by its name. Also do partial match.
     *
     * @param name name to be matched
     * @return Store that partially matches the name, or an empty list if no such Store exist
     */
    List<Store> findByNameContaining(String name);

    /**
     * Find Store by its uuid
     *
     * @param id uuid of the store
     * @return the Store if it was found
     * @throws ResourceNotFoundException if this store does not exist
     */
    Store findById(UUID id) throws ResourceNotFoundException;

    /***
     * Find Store by its name. Have to be fully matching.
     * @param name name to be matched
     * @return Store with matching name. Or an empty list if no such Store exist
     */
    List<Store> findByName(String name);

    /**
     * Find all Store that have avgRating greater than or equal to param rating
     *
     * @param avgRating avgRating to be compared with
     * @return Store that has avgRating greater than or equal to param rating, or an empty list if no such Store exist
     * @throws IllegalArgumentException when avgRating is out of bound
     */
    List<Store> findByAvgRatingGreaterThanEqual(float avgRating, boolean sorted);

    /**
     * Find store that contains the specified Item
     *
     * @param id id of the item entity
     * @return the store satisfying the condition
     * @throws ResourceNotFoundException thrown when no such store exist
     */
    Store findByItem(UUID id) throws ResourceNotFoundException;

    /**
     * Find store that contains the specified Rating
     *
     * @param id id of the Rating entity
     * @return the store satisfying the condition
     * @throws ResourceNotFoundException thrown when no such store exist
     */
    Store findByRating(UUID id) throws ResourceNotFoundException;
}
