package com.boba.bobabuddy.core.service.store;

import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;

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
     * @param sort sort the returned list
     */
    List<Store> findAll(Sort sort);

    /**
     * Find Store by its location
     *
     * @param location location of the store
     * @param sort sort the returned list
     * @return the Store if it was found. Otherwise, return null.
     */
    List<Store> findByLocation(String location, Sort sort);

    /**
     * Find Store by its name. Also do partial match.
     *
     * @param name name to be matched
     * @param sort sort the returned list
     * @return Store that partially matches the name, or an empty list if no such Store exist
     */
    List<Store> findByNameContaining(String name, Sort sort);

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
     * @param sort sort the returned list
     * @return Store with matching name. Or an empty list if no such Store exist
     */
    List<Store> findByName(String name, Sort sort);

    /**
     * Find all Store that have avgRating greater than or equal to param rating
     *
     * @param avgRating avgRating to be compared with
     * @param sort sort the returned list
     * @return Store that has avgRating greater than or equal to param rating, or an empty list if no such Store exist
     * @throws IllegalArgumentException when avgRating is out of bound
     */
    List<Store> findByAvgRatingGreaterThanEqual(float avgRating, Sort sort);

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
