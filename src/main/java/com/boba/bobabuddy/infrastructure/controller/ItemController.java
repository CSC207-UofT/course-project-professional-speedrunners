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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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



    @Autowired
    public ItemController(ICreateItem createItem, IFindItem findItem, IRemoveItem removeItem, IUpdateItem updateItem) {
        this.createItem = createItem;
        this.findItem = findItem;
        this.removeItem = removeItem;
        this.updateItem = updateItem;
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
    @PostMapping(path = "/api/store/{storeId}/item/")
    public Item createItem(@RequestBody CreateItemRequest createItemRequest, @PathVariable UUID storeId)
            throws ResourceNotFoundException, DuplicateResourceException {
        return createItem.create(createItemRequest.toItem(), storeId);
    }

    /***
     * Query for all exiting item resource
     * @return list of Item resources exist in the database
     */
    @GetMapping(path = "/api/item/")
    public List<Item> findAll() {
        return findItem.findAll();
    }

    /***
     * query for an item resource with matching id.
     * @param id the primary uuid key of the resource
     * @return Item with matching UUID, which will be automatically converted to JSON and send it to the caller.
     * @throws ResourceNotFoundException thrown when the item was not found
     */
    // Exceptions thrown in controller class will be handled automatically by SpringFramework
    @GetMapping(path = "/api/item/{id}")
    public Item findById(@PathVariable UUID id) throws ResourceNotFoundException {
        return findItem.findById(id);
    }

    /***
     * query for item resources that have matching name.
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/item/", params = "name")
    public List<Item> findByName(@RequestParam("name") String name) {
        return findItem.findByName(name);
    }

    /***
     * query for item resource that partially matches the provided name
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/item/", params = "name-contain")
    public List<Item> findByNameContaining(@RequestParam("name-contain") String nameContain) {
        return findItem.findByNameContaining(nameContain);
    }

    /***
     * query for an item resource with a matching store id
     * @return List of item resources matching the query.
     */
    // GetMapping maps a GET request to the endpoint defined by path parameter. A GET request initiates a query.
    @GetMapping(path = "/api/item/", params = "storeId")
    public List<Item> findByStore(@RequestParam("storeId") UUID storeId) {
        return findItem.findByStore(storeId);
    }

    /***
     * query for item resources that have price less than equal to a given value
     * @param price the price used for comparison
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/item/", params = "price-leq")
    // @PathVariable annotation maps the value present at the specified location in the URL to the method parameter.
    // For example, <Get ~/api/item/price-less-or-equal/15.5> will initiate the method call
    // findByPriceLessThanEqual(15.5).
    public List<Item> findByPriceLessThanEqual(@RequestParam("price-leq") float price,
                                               @RequestParam(defaultValue = "false") boolean sorted) {
        return findItem.findByPriceLessThanEqual(price, sorted);
    }

    /***
     * query for item resources that have rating greater than or equal to a given value
     * @param rating the rating used for comparison
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/item/", params = "rating-geq")
    public List<Item> findByAvgRatingGreaterThanEqual(@RequestParam("rating-geq") float rating,
                                                      @RequestParam(defaultValue = "false") boolean sorted) {
        return findItem.findByAvgRatingGreaterThanEqual(rating, sorted);
    }

    @PutMapping(path = "/api/item/{id}")
    public Item updateItem(@RequestBody Item newItem, @PathVariable UUID id) throws ResourceNotFoundException,
            DifferentResourceException {
        Item itemToUpdate = findById(id);
        return updateItem.updateItem(itemToUpdate, newItem);
    }

    @DeleteMapping(path = "/api/item/{id}")
    public Item removeItem(@PathVariable UUID id) throws ResourceNotFoundException {
        return removeItem.removeById(id);
    }


}
