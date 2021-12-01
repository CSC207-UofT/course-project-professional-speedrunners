package com.boba.bobabuddy.core.service.item;

import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

/**
 * Usecase Input Boundary
 */
public interface FindItemService {
    /**
     * Find an Item by its Store.
     * The query matches the uuid of the store.
     *
     * @param id uuid of the Store object
     * @param sort
     * @return A list of Item that belongs to the store. Or an empty list if no Item is sold from this store.
     */
    List<Item> findByStore(UUID id, Sort sort);

    /**
     * Find Item by its uuid
     *
     * @param id uuid of the item
     * @return the Item if it was found.
     * @throws ResourceNotFoundException Thrown when no item with matching ID exist in the database
     */
    Item findById(UUID id) throws ResourceNotFoundException;

    /**
     * Find all items that exist in the database
     *
     * @return list of all items in the database, or an empty list if the database is empty
     * @param sort
     */
    List<Item> findAll(Sort sort);

    /**
     * Find Item by its name. Have to be fully matching.
     *
     * @param name name to be matched
     * @param sort
     * @return Item with matching name. Or an empty list if no such Item exist
     */
    List<Item> findByName(String name, Sort sort);

    /**
     * Find Item by its name. Also do partial match.
     *
     * @param name name to be matched
     * @param sort
     * @return Item that partially matches the name, or an empty list if no such Item exist
     */
    List<Item> findByNameContaining(String name, Sort sort);

    /**
     * Find all items that have price less than or equal to param price
     *
     * @param price  price to be compared with
     * @param sort
     * @return Items that have price less than or equal to param price, or an empty list if no such Item exist
     */
    List<Item> findByPriceLessThanEqual(float price, Sort sort);

    /**
     * Find all items that have avgRating greater than or equal to param rating
     *
     * @param avgRating avgRating to be compared with
     * @param sort
     * @return Items that has avgRating greater than or equal to param rating, or an empty list if no such Item exist
     * @throws IllegalArgumentException when avgRating is out of bound
     */
    List<Item> findByAvgRatingGreaterThanEqual(float rating, Sort sort);

    /**
     * Find item that has a matching rating object
     *
     * @param id id of the Rating entity
     * @return Item if such item exist
     * @throws ResourceNotFoundException thrown when no Item possessing this rating object exist
     */
    Item findByRating(UUID id) throws ResourceNotFoundException;
}
