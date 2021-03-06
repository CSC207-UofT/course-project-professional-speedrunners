package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.service.item.CreateItemService;
import com.boba.bobabuddy.core.service.item.FindItemService;
import com.boba.bobabuddy.core.service.item.RemoveItemService;
import com.boba.bobabuddy.core.service.item.UpdateItemService;
import com.boba.bobabuddy.framework.util.DtoConverter;
import com.boba.bobabuddy.framework.util.SortQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for Item related api calls
 */
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final CreateItemService createItem;
    private final RemoveItemService removeItem;
    private final UpdateItemService updateItem;
    private final FindItemService findItem;
    private final DtoConverter<Item, ItemDto> converter;


    /**
     * POST HTTP requests for creating item resource
     *
     * @param createItemRequest Request class that contains data necessary to construct an Item entity.
     * @return Item that was created, in JSON + HAL
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/stores/{storeId}/items")
    @PreAuthorize("@FindStoreService.findById(#storeId).getOwner() == authentication.principal.username || hasAuthority('ROLE_ADMIN')")
    public ItemDto createItem(@RequestBody ItemDto createItemRequest, @PathVariable UUID storeId) {
        return converter.convertToDto(createItem.create(createItemRequest, storeId));
    }

    /**
     * Root endpoint for item resources
     *
     * @return list of Item resources exist in the database
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/items")
    public List<ItemDto> findAll(@RequestParam(defaultValue = "price") String sortBy) {
        return converter.convertToDtoList(findItem.findAll(SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for an item resource with matching id.
     *
     * @param id the primary uuid key of the resource
     * @return Item resource with matching UUID
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/items/{id}")
    public ItemDto findById(@PathVariable UUID id) {
        return converter.convertToDto(findItem.findById(id));
    }

    /**
     * Handles GET requests for item resources that have matching name.
     *
     * @param name name to match for
     * @return collection of item resources that match the query
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/items", params = "name")
    public List<ItemDto> findByName(@RequestParam("name") String name, @RequestParam(defaultValue = "price") String sortBy) {
        return converter.convertToDtoList(findItem.findByName(name, SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for item resource that partially matches the provided name
     *
     * @param name name to match for
     * @return collection of item resources that match the query
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/items", params = "name-contain")
    public List<ItemDto> findByNameContaining(@RequestParam("name-contain") String name,
                                              @RequestParam(defaultValue = "price") String sortBy) {
        return converter.convertToDtoList(findItem.findByNameContaining(name, SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for an item resources that belongs to a store
     *
     * @param id id of the store resource
     * @return list of item resources that belong to the specified store
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/stores/{id}/items")
    public List<ItemDto> findByStore(@PathVariable UUID id,
                                     @RequestParam(defaultValue = "price") String sortBy) {
        return converter.convertToDtoList(findItem.findByStore(id, SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for an item resources that belongs to a category
     *
     * @param name name of the category
     * @return list of item resources that belong to the specified store
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/category", params = "name, sortBy")
    public List<ItemDto> findByCategory(@RequestParam("name") String name,
                                       @RequestParam(defaultValue = "price") String sortBy){
        return converter.convertToDtoList(findItem.findByCategory(name, SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for item resources that have price less than equal to a given value
     *
     * @param price the price used for comparison
     * @return list of item resources that match the query.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/items", params = {"price-leq"})
    public List<ItemDto> findByPriceLessThanEqual(@RequestParam("price-leq") double price,
                                                  @RequestParam(defaultValue = "price") String sortBy) {
        return converter.convertToDtoList(
                findItem.findByPriceLessThanEqual(price, SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for item resources that have rating greater than or equal to a given value
     *
     * @param rating the rating used for comparison
     * @return list of item resources that match the query.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/items", params = {"rating-geq"})
    public List<ItemDto> findByAvgRatingGreaterThanEqual(@RequestParam("rating-geq") double rating,
                                                         @RequestParam(defaultValue = "price") String sortBy) {
        return converter.convertToDtoList(
                findItem.findByAvgRatingGreaterThanEqual(rating, SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handle GET request to find an item resource that contains a particular rating
     *
     * @param id id of the rating
     * @return item resource that match the query
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/items", params = "ratingId")
    public ItemDto findByRating(@RequestParam("ratingId") UUID id) {
        return converter.convertToDto(findItem.findByRating(id));
    }

    /**
     * Handles PUT request to update an existing item resource
     *
     * @param newItem the new modified item
     * @param id      item resource to be updated
     * @return item resource after the modification
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/items/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') || principal.username == @FindItemService.findById(#id).getStore().getOwner()")
    public ItemDto updateItem(@RequestBody ItemDto newItem, @PathVariable UUID id) {
        return converter.convertToDto(updateItem.updateItem(id, newItem));
    }

    /**
     * Handles PUT request to update the price of an existing item resource
     *
     * @param price the new price
     * @param id the UUID of the Item to be updated
     * @return the updated item
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/items/{id}", params = "price")
    public ItemDto updateItemPrice(@RequestParam double price, @PathVariable UUID id) {
        return converter.convertToDto(updateItem.updateItemPrice(id, price));
    }
    /**
     * Handles PUT request to update the image of an existing item resource
     *
     * @param imageUrl URL of the image
     * @param id the UUID of the Item to be updated
     * @return the updated item
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/items/{id}", params = "imageUrl")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') || principal.username == @FindItemService.findById(#id).getStore().getOwner()")
    public ItemDto updateItemImage(@RequestParam String imageUrl, @PathVariable UUID id){
        return converter.convertToDto(updateItem.updateItemImage(id, imageUrl));
    }

    /**
     * Handles PUT request to add a category to an existing item resource
     *
     * @param categoryToAdd name of category to be added
     * @param id the UUID of the Item to be updated
     * @return the updated item
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') || principal.username == @FindItemService.findById(#id).getStore().getOwner()")
    @PutMapping(path = "/items/{id}", params = "categoryToAdd")
    public ItemDto addCategory(@RequestParam String categoryToAdd, @PathVariable UUID id) {
        return converter.convertToDto(updateItem.addCategory(id, categoryToAdd));
    }

    /**
     * Handles PUT request to remove a category from an existing item resource
     *
     * @param id the UUID of the Item to be updated
     * @return the updated item
     */
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') || principal.username == @FindItemService.findById(#id).getStore().getOwner()")
    @PutMapping(path = "/items/{id}", params = "categoryToRemove")
    public ItemDto removeCategory(@RequestParam String categoryToRemove, @PathVariable UUID id) {
        return converter.convertToDto(updateItem.removeCategory(id, categoryToRemove));
    }

    /**
     * Handle DELETE request to delete an item resource from the system
     * Respond with 204 NO CONTENT
     *
     * @param id id of the resource to be deleted
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/items/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') || principal.username == @FindItemService.findById(#id).getStore().getOwner()")
    public void removeItem(@PathVariable UUID id) {
        removeItem.removeById(id);
    }
}
