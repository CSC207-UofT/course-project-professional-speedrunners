package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.DuplicateResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.itemport.ICreateItem;
import com.boba.bobabuddy.core.usecase.port.itemport.IFindItem;
import com.boba.bobabuddy.core.usecase.port.itemport.IRemoveItem;
import com.boba.bobabuddy.core.usecase.port.itemport.IUpdateItem;
import com.boba.bobabuddy.core.usecase.port.request.CreateItemRequest;
import com.boba.bobabuddy.infrastructure.assembler.ItemResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/***
 * REST controller for Item related api calls
 * Note that the current structure and behaviour of these api definitions are not necessarily RESTful. At this moment
 * in time it only server as an example of roughly how an API controller should be set up.
 */
@RestController
public class ItemController {

    // fields for all Item usecase, with interface
    private final ICreateItem createItem;
    private final IRemoveItem removeItem;
    private final IUpdateItem updateItem;
    private final IFindItem findItem;

    private final ItemResourceAssembler assembler;


    @Autowired
    public ItemController(ICreateItem createItem, IFindItem findItem, IRemoveItem removeItem, IUpdateItem updateItem, ItemResourceAssembler assembler) {
        this.createItem = createItem;
        this.findItem = findItem;
        this.removeItem = removeItem;
        this.updateItem = updateItem;
        this.assembler = assembler;
        this.assembler.setBasePath("/api");
    }

    /***
     * add the Item resource into the database.
     * @param createItemRequest Request class that contains data necessary to construct an Item entity.
     * @return Item that was constructed, which will be automatically converted to JSON and send it to the caller.
     */
    // Simply, a POST request called to the endpoint <domain address>/api/item/ will be mapped to this method.
    // the <produces> parameter decides how the return value will be converted to and be sent back to the caller,
    // in this case a JSON.
    // the @RequestBody annotation indicates that the body of an HTTP request will be interpreted as an POJO
    // representation by HTTPMessageConverter, which converts it to the parameter type (in this case createItemRequest).
    // Then, it will be passed to the method.
    @PostMapping(path = "/api/stores/{storeId}/items")
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
     * Query for all exiting item resource
     * @return list of Item resources exist in the database
     */
    @GetMapping(path = "/api/items")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findAll()));
    }

    /***
     * query for an item resource with matching id.
     * @param id the primary uuid key of the resource
     * @return Item with matching UUID, which will be automatically converted to JSON and send it to the caller.
     * @throws ResourceNotFoundException thrown when the item was not found
     */
    // Exceptions thrown in controller class will be handled automatically by SpringFramework
    @GetMapping(path = "/api/items/{id}")
    public ResponseEntity<EntityModel<Item>> findById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(assembler.toModel(findItem.findById(id)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /***
     * query for item resources that have matching name.
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/items", params = "name")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findByName(name)));
    }

    /***
     * query for item resource that partially matches the provided name
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/items", params = "name-contain")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findByNameContaining(@RequestParam("name-contain") String nameContain) {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findByNameContaining(nameContain)));
    }

    /***
     * query for an item resource with a matching store id
     * @return List of item resources matching the query.
     */
    // GetMapping maps a GET request to the endpoint defined by path parameter. A GET request initiates a query.
    @GetMapping(path = "/api/stores/{id}/items")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findByStore(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findByStore(id)));
    }

    /***
     * query for item resources that have price less than equal to a given value
     * @param price the price used for comparison
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/items", params = "price-leq")
    // @PathVariable annotation maps the value present at the specified location in the URL to the method parameter.
    // For example, <Get ~/api/item/price-less-or-equal/15.5> will initiate the method call
    // findByPriceLessThanEqual(15.5).
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findByPriceLessThanEqual(@RequestParam("price-leq") float price,
                                                                                       @RequestParam(defaultValue = "false") boolean sorted) {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findByPriceLessThanEqual(price, sorted)));
    }

    /***
     * query for item resources that have rating greater than or equal to a given value
     * @param rating the rating used for comparison
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/items", params = "rating-geq")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> findByAvgRatingGreaterThanEqual(@RequestParam("rating-geq") float rating,
                                                                                              @RequestParam(defaultValue = "false") boolean sorted) {
        return ResponseEntity.ok(assembler.toCollectionModel(findItem.findByAvgRatingGreaterThanEqual(rating, sorted)));
    }

    @PutMapping(path = "/api/items/{id}")
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

    @DeleteMapping(path = "/api/items/{id}")
    public ResponseEntity<EntityModel<Item>> removeItem(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(assembler.toModel(removeItem.removeById(id)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/api/items", params = "ratingId")
    public ResponseEntity<EntityModel<Item>> findByRating(@RequestParam("ratingId") UUID id){
        try{
            return ResponseEntity.ok(assembler.toModel(findItem.findByRating(id)));
        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

    }
}
