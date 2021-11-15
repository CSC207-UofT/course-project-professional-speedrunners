package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.item.port.ICreateItem;
import com.boba.bobabuddy.core.usecase.item.port.IFindItem;
import com.boba.bobabuddy.core.usecase.item.port.IRemoveItem;
import com.boba.bobabuddy.core.usecase.item.port.IUpdateItem;
import com.boba.bobabuddy.infrastructure.assembler.ItemResourceAssembler;
import com.boba.bobabuddy.infrastructure.dto.FullDtoConverter;
import com.boba.bobabuddy.infrastructure.dto.ItemDto;
import com.boba.bobabuddy.infrastructure.dto.SimpleItemDto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


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
    private final FullDtoConverter<Item, SimpleItemDto, ItemDto> dtoConverter;

    // Put response entities into a wrapper that stores hypermedia links (HATEOAS)
    private final ItemResourceAssembler assembler;

    public ItemController(ICreateItem createItem, IFindItem findItem, IRemoveItem removeItem, IUpdateItem updateItem,
                          ModelMapper mapper, ItemResourceAssembler assembler) {
        this.createItem = createItem;
        this.findItem = findItem;
        this.removeItem = removeItem;
        this.updateItem = updateItem;
        this.assembler = assembler;
        this.dtoConverter = new FullDtoConverter<>(mapper, Item.class, SimpleItemDto.class, ItemDto.class);

    }

    /***
     * POST HTTP requests for creating item resource
     * @param createItemRequest Request class that contains data necessary to construct an Item entity.
     * @return Item that was created, in JSON + HAL
     */
    @PostMapping(path = "/stores/{storeId}/items")
    public ResponseEntity<EntityModel<ItemDto>> createItem(@RequestBody SimpleItemDto createItemRequest,
                                                        @PathVariable UUID storeId) {
        Item itemToPresent = createItem.create(dtoConverter.convertToEntityFromSimple(createItemRequest), storeId);
        return ResponseEntity.created(linkTo(methodOn(ItemController.class).findById(itemToPresent.getId())).toUri()).body(assembler.toModel(dtoConverter.convertToDto(itemToPresent)));
    }

    /***
     * Root endpoint for item resources
     * @return list of Item resources exist in the database
     */
    @GetMapping(path = "/items")
    public ResponseEntity<CollectionModel<EntityModel<ItemDto>>> findAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findItem.findAll())));
    }

    /***
     * Handles GET requests for an item resource with matching id.
     * @param id the primary uuid key of the resource
     * @return Item resource with matching UUID
     */
    @GetMapping(path = "/items/{id}")
    public ResponseEntity<EntityModel<ItemDto>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toModel(dtoConverter.convertToDto(findItem.findById(id))));
    }

    /***
     * Handles GET requests for item resources that have matching name.
     * @param name name to match for
     * @return collection of item resources that match the query
     */
    @GetMapping(path = "/items", params = "name")
    public ResponseEntity<CollectionModel<EntityModel<ItemDto>>> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findItem.findByName(name))));
    }

    /***
     * Handles GET requests for item resource that partially matches the provided name
     * @param name name to match for
     * @return collection of item resources that match the query
     */
    @GetMapping(path = "/items", params = "name-contain")
    public ResponseEntity<CollectionModel<EntityModel<ItemDto>>> findByNameContaining(@RequestParam("name-contain") String name) {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findItem.findByNameContaining(name))));
    }

    /***
     * Handles GET requests for an item resources that belongs to a store
     * @param id id of the store resource
     * @return list of item resources that belong to the specified store
     */
    @GetMapping(path = "/stores/{id}/items")
    public ResponseEntity<CollectionModel<EntityModel<ItemDto>>> findByStore(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findItem.findByStore(id))));
    }

    /***
     * Handles GET requests for item resources that have price less than equal to a given value
     * @param price the price used for comparison
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/items", params = "price-leq")
    public ResponseEntity<CollectionModel<EntityModel<ItemDto>>> findByPriceLessThanEqual(@RequestParam("price-leq") float price,
                                                                                       @RequestParam(defaultValue = "false") boolean sorted) {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findItem.findByPriceLessThanEqual(price, sorted))));
    }

    /***
     * Handles GET requests for item resources that have rating greater than or equal to a given value
     * @param rating the rating used for comparison
     * @return list of item resources that match the query.
     */
    @GetMapping(path = "/items", params = "rating-geq")
    public ResponseEntity<CollectionModel<EntityModel<ItemDto>>> findByAvgRatingGreaterThanEqual(@RequestParam("rating-geq") float rating,
                                                                                              @RequestParam(defaultValue = "false") boolean sorted) {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findItem.findByAvgRatingGreaterThanEqual(rating, sorted))));
    }

    /***
     * Handle GET request to find an item resource that contains a particular rating
     * @param id id of the rating
     * @return item resource that match the query
     */
    @GetMapping(path = "/items", params = "ratingId")
    public ResponseEntity<EntityModel<ItemDto>> findByRating(@RequestParam("ratingId") UUID id) {

        return ResponseEntity.ok(assembler.toModel(dtoConverter.convertToDto(findItem.findByRating(id))));

    }

    /***
     * Handles PUT request to update an existing item resource
     * @param newItem the new modified item
     * @param id item resource to be updated
     * @return item resource after the modification
     */
    @PutMapping(path = "/items/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ItemDto>> updateItem(@RequestBody SimpleItemDto newItem, @PathVariable UUID id) {

        Item itemToUpdate = findItem.findById(id);
        return ResponseEntity.ok(assembler.toModel(dtoConverter.convertToDto(updateItem.updateItem(itemToUpdate, newItem))));

    }

    /***
     * Handle DELETE request to delete an item resource from the system
     * @param id id of the resource to be deleted
     * @return NO_CONTENT http status
     */
    @DeleteMapping(path = "/items/{id}")
    public ResponseEntity<?> removeItem(@PathVariable UUID id) {
        removeItem.removeById(id);
        return ResponseEntity.noContent().build();

    }



}
