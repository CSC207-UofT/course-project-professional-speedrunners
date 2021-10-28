package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.port.IRequest.IItemIn.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ItemController {

    private final ICreateItem createItem;
    private final IFindItem findItem;
    private final IRemoveItem removeItem;
    private final IUpdateItem updateItem;

    @Autowired
    public ItemController(ICreateItem createItem, IFindItem findItem, IRemoveItem removeItem, IUpdateItem updateItem){
        this.createItem = createItem;
        this.findItem = findItem;
        this.removeItem = removeItem;
        this.updateItem = updateItem;
    }

    @PostMapping(path = "/api/item/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Item createItem(@RequestBody CreateItemRequest createItemRequest){
        return createItem.create(createItemRequest.toItem());
    }

    @GetMapping(path = "/api/item/store", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Item> findByStore(@RequestBody FindByStoreRequest findByStoreRequest){
        return findItem.findByStore(findByStoreRequest.getId());
    }

    @GetMapping(path = "/api/item/id")
    public Optional<Item> findById(@RequestBody UUID id){
        return findItem.findById(id);
    }

    @GetMapping(path = "/api/item/")
    public List<Item> findAll(){
        return findItem.findAll();
    }

    @GetMapping(path = "/api/item/name")
    public List<Item> findByName(@RequestBody String name){
        return findItem.findByName(name);
    }

    @GetMapping(path = "/api/item/contain/{name}")
    public List<Item> findByNameContaining(@PathVariable String name){
        return findItem.findByNameContaining(name);
    }

    @GetMapping(path = "/api/item/price-less-or-equal/{price}")
    public List<Item> findByPriceLessThanEqual(@PathVariable float price){
        return findItem.findByPriceLessThanEqual(price);
    }

    @GetMapping(path = "/api/item/rating-greater-or-equal/{rating}")
    public List<Item> findByAvgRatingGreaterThanEqual(@PathVariable float rating){
        return findItem.findByAvgRatingGreaterThanEqual(rating);
    }








}
