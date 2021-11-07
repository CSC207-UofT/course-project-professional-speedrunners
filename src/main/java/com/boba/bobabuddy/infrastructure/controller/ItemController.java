package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.item.port.ICreateItem;
import com.boba.bobabuddy.core.usecase.item.port.IFindItem;
import com.boba.bobabuddy.core.usecase.item.port.IRemoveItem;
import com.boba.bobabuddy.core.usecase.item.port.IUpdateItem;
import com.boba.bobabuddy.core.usecase.request.CreateItemRequest;
import com.boba.bobabuddy.infrastructure.assembler.ItemResourceAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/***
 * REST controller for Item related api calls
 */
@RestController
public class ItemController {

    private final ICreateItem createItem;
    private final IRemoveItem removeItem;
    private final IUpdateItem updateItem;
    private final IFindItem findItem;

    // Put response entities into a wrapper that stores hypermedia links (HATEOAS)
    private final ItemResourceAssembler assembler;

    public ItemController(ICreateItem createItem, IFindItem findItem, IRemoveItem removeItem, IUpdateItem updateItem, ItemResourceAssembler assembler) {
        this.createItem = createItem;
        this.findItem = findItem;
        this.removeItem = removeItem;
        this.updateItem = updateItem;
        this.assembler = assembler;
    }

    /***
     * POST HTTP requests for creating item resource
     * @param createItemRequest Request class that contains data necessary to construct an Item entity.
     * @return Item that was created, in JSON + HAL
     */
    @PostMapping(path = "/stores/{storeId}/items")
    public ResponseEntity<EntityModel<Item>> createItem(@RequestBody CreateItemRequest createItemRequest,
                                                        @PathVariable UUID storeId) {
        try {
            Item itemToPresent = createItem.create(createItemRequest.toItem(), storeId);
            return ResponseEntity.ok(assembler.toModel(itemToPresent));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DuplicateResourceException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    /***
     * Root endpoint for item resources
     * @return list of Item resources exist in the database
     */
    @GetMapping(path = "/items")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findAll()));
    }

    /***
     * Handles GET requests for an item resource with matching id.
     * @param id the primary uuid key of the resource
     * @return Item resource with matching UUID
     */
    @GetMapping(path = "/items/{id}")
    public ResponseEntity<EntityModel<Item>> findById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(assembler.toModel(findItem.findById(id)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /***
     * Handles GET requests for item resources that have matching name.
     * @param name name to match for
     * @return collection of item resources that match the query
     */
    @GetMapping(path = "/items", params = "name")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findByName(name)));
    }

    /***
     * Handles GET requests for item resource that partially matches the provided name
     * @param name name to match for
     * @return collection of item resources that match the query
     */
    @GetMapping(path = "/items", params = "name-contain")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findByNameContaining(@RequestParam("name-contain") String name) {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findByNameContaining(name)));
    }

    /***
     * Handles GET requests for an item resources that belongs to a store
     * @param id id of the store resource
     * @return list of item resources that belong to the specified store
     */
    @GetMapping(path = "/stores/{id}/items")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findByStore(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findByStore(id)));
    }

    /***
     * Handles GET requests for item resources that have price less than equal to a given value
     * @param price the price used for comparison
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/items", params = "price-leq")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findByPriceLessThanEqual(@RequestParam("price-leq") float price,
                                                                                       @RequestParam(defaultValue = "false") boolean sorted) {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findByPriceLessThanEqual(price, sorted)));
    }

    /***
     * Handles GET requests for item resources that have rating greater than or equal to a given value
     * @param rating the rating used for comparison
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/items", params = "rating-geq")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findByAvgRatingGreaterThanEqual(@RequestParam("rating-geq") float rating,
                                                                                              @RequestParam(defaultValue = "false") boolean sorted) {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findByAvgRatingGreaterThanEqual(rating, sorted)));
    }

    /***
     * Handles PUT request to update an existing item resource
     * @param newItem the new modified item
     * @param id item resource to be updated
     * @return item resource after the modification
     */
    @PutMapping(path = "/items/{id}")
    public ResponseEntity<EntityModel<Item>> updateItem(@RequestBody Item newItem, @PathVariable UUID id) {
        try {
            Item itemToUpdate = findItem.findById(id);
            return ResponseEntity.ok(assembler.toModel(updateItem.updateItem(itemToUpdate, newItem)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DifferentResourceException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    /***
     * Handle DELETE request to delete an item resource from the system
     * @param id id of the resource to be deleted
     * @return NO_CONTENT http status
     */
    @DeleteMapping(path = "/items/{id}")
    public ResponseEntity<?> removeItem(@PathVariable UUID id) {
        try {
            removeItem.removeById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /***
     * Handle GET request to find an item resource that contains a particular rating
     * @param id id of the rating
     * @return item resource that match the query
     */
    @GetMapping(path = "/items", params = "ratingId")
    public ResponseEntity<EntityModel<Item>> findByRating(@RequestParam("ratingId") UUID id) {
        try {
            return ResponseEntity.ok(assembler.toModel(findItem.findByRating(id)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

    }
}
