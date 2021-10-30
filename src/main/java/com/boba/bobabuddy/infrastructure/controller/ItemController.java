package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.item.exceptions.ItemNotFoundException;
import com.boba.bobabuddy.core.usecase.port.itemport.ICreateItem;
import com.boba.bobabuddy.core.usecase.port.itemport.IFindItem;
import com.boba.bobabuddy.core.usecase.port.itemport.IRemoveItem;
import com.boba.bobabuddy.core.usecase.port.itemport.IUpdateItem;
import com.boba.bobabuddy.core.usecase.port.request.CreateItemRequest;
import com.boba.bobabuddy.core.usecase.port.request.FindByIdRequest;
import com.boba.bobabuddy.core.usecase.port.request.FindByNameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * REST controller for Item related api calls
 * Note that the current structure and behaviour of these api definitions are not necessarily RESTful. At this moment
 * in time it only server as an example of roughly how an API controller should be set up.
 */
@RestController
public class ItemController {

    // fields for all Item usecase, with interface
    private final ICreateItem createItem;
    private final IFindItem findItem;
    private final IRemoveItem removeItem;
    private final IUpdateItem updateItem;

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
    @PostMapping(path = "/api/item/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Item createItem(@RequestBody CreateItemRequest createItemRequest) {
        return createItem.create(createItemRequest.toItem());
    }

    /***
     * query for an item resource with a matching store id
     * @param findByIdRequest Request class containing a UUID
     * @return List of item resources matching the query.
     */
    // GetMapping maps a GET request to the endpoint defined by path parameter. A GET request initiates a query.
    @GetMapping(path = "/api/item/store", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Item> findByStore(@RequestBody FindByIdRequest findByIdRequest) {
        return findItem.findByStore(findByIdRequest.getId());
    }

    /***
     * query for an item resource with matching id.
     * @param findByIdRequest Request class containing a UUID
     * @return Item with matching UUID, which will be automatically converted to JSON and send it to the caller.
     * @throws ItemNotFoundException thrown when the item was not found
     */
    // Exceptions thrown in controller class will be handled automatically by SpringFramework
    @GetMapping(path = "/api/item/id")
    public Item findById(@RequestBody FindByIdRequest findByIdRequest) throws ItemNotFoundException {
        return findItem.findById(findByIdRequest.getId());
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
     * query for item resources that have matching name.
     * @param findByNameRequest request class contain a name in String.
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/item/name")
    public List<Item> findByName(@RequestBody FindByNameRequest findByNameRequest) {
        return findItem.findByName(findByNameRequest.getName());
    }

    /***
     * query for item resource that partially matches the provided name
     * @param findByNameRequest request class contain a name in String.
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/item/name-contain")
    public List<Item> findByNameContaining(@RequestBody FindByNameRequest findByNameRequest) {
        return findItem.findByNameContaining(findByNameRequest.getName());
    }

    /***
     * query for item resources that have price less than equal to a given value
     * @param price the price used for comparison
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/item/price-less-or-equal/{price}")
    // @PathVariable annotation maps the value present at the specified location in the URL to the method parameter.
    // For example, <Get ~/api/item/price-less-or-equal/15.5> will initiate the method call
    // findByPriceLessThanEqual(15.5).
    public List<Item> findByPriceLessThanEqual(@PathVariable float price) {
        return findItem.findByPriceLessThanEqual(price);
    }

    /***
     * query for item resources that have rating greater than or equal to a given value
     * @param rating the rating used for comparison
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/api/item/rating-greater-or-equal/{rating}")
    public List<Item> findByAvgRatingGreaterThanEqual(@PathVariable float rating) {
        return findItem.findByAvgRatingGreaterThanEqual(rating);
    }


}
