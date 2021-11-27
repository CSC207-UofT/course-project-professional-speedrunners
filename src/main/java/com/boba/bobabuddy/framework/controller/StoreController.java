package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.service.store.CreateStoreService;
import com.boba.bobabuddy.core.service.store.FindStoreService;
import com.boba.bobabuddy.core.service.store.RemoveStoreService;
import com.boba.bobabuddy.core.service.store.UpdateStoreService;
import com.boba.bobabuddy.framework.converter.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


/**
 * REST controller for Store related api calls
 */

@RestController
@RequiredArgsConstructor
public class StoreController {

    //fields for all Store usecase, with interface
    private final CreateStoreService createStore;
    private final RemoveStoreService removeStore;
    private final UpdateStoreService updateStore;
    private final FindStoreService findStore;
    private final DtoConverter<Store, StoreDto> converter;


    /**
     * Post HTTP requests for creating store resource
     *
     * @param createStoreRequest Request class that contains data necessary to construct a Store entity.
     * @return Store that was constructed, which will be automatically converted to JSON and send it to the caller.
     */
    @PostMapping(path = "/stores")
    public StoreDto createStore(@RequestBody StoreDto createStoreRequest) {
        return converter.convertToDto(createStore.create(createStoreRequest));

    }

    /**
     * Root endpoint for store resources
     *
     * @return collection of store resources exist in the database
     */
    @GetMapping(path = "/stores")
    public List<StoreDto> findAll() {
        return converter.convertToDtoList(findStore.findAll());
    }

    /**
     * Handles GET requests for a store resource with matching id.
     *
     * @param id the primary uuid key of the resource
     * @return the store resource with matching UUID
     */
    @GetMapping(path = "/stores/{id}")
    public StoreDto findById(@PathVariable UUID id) {
        return converter.convertToDto(findStore.findById(id));
    }

    /**
     * Handles GET requests for a store resource that has a certain item
     *
     * @param id id of the item resource
     * @return the store resource that has the specified item
     */
    @GetMapping(path = "/stores", params = "itemId")
    public StoreDto findByItem(@RequestParam("itemId") UUID id) {
        return converter.convertToDto(findStore.findByItem(id));

    }

    /**
     * Handles GET requests for store resources that have matching location.
     *
     * @param location location of required store
     * @return collection of store resources that match the query.
     */
    @GetMapping(path = "/stores", params = "location")
    public List<StoreDto> findByLocation(@RequestParam("location") String location) {
        return converter.convertToDtoList(findStore.findByLocation(location));
    }

    /**
     * Handles GET requests for store resources that have matching name
     *
     * @param name name of required store
     * @return collection of store resources that match the query.
     */
    @GetMapping(path = "/stores", params = "name")
    public List<StoreDto> findByName(@RequestParam("name") String name) {
        return converter.convertToDtoList(findStore.findByName(name));
    }

    /**
     * Handles GET requests for store resources that partially matches the provided name
     *
     * @param nameContain name to match for
     * @return collection of store resources that match the query.
     */
    @GetMapping(path = "/stores", params = "name-contain")
    public List<StoreDto> findByNameContaining(@RequestParam("name-contain") String nameContain) {
        return converter.convertToDtoList(findStore.findByNameContaining(nameContain));
    }

    /**
     * Handles GET requests for store resources that have rating greater than or equal to a given value
     *
     * @param rating the rating used for comparison
     * @return collection of store resources that match the query.
     */
    @GetMapping(path = "/stores", params = "rating-geq")
    public List<StoreDto> findByAvgRatingGreaterThanEqual(@RequestParam("rating-geq") float rating,
                                                          @RequestParam(defaultValue = "false") boolean sorted) {
        return converter.convertToDtoList(findStore.findByAvgRatingGreaterThanEqual(rating, sorted));
    }

    /**
     * Handle GET request to find a store resource that contains a particular rating
     *
     * @param id id of the rating
     * @return a store resource that match teh query
     */
    @GetMapping(path = "/stores", params = "ratingId")
    public StoreDto findByRating(@RequestParam("ratingId") UUID id) {
        return converter.convertToDto(findStore.findByRating(id));

    }

    /**
     * Handles PUT request to update an existing store resource
     *
     * @param id         store resource to be updated
     * @param storePatch the same store with updated fields.
     * @return the store resource after the modification
     */
    @PutMapping(path = "/stores/{id}")
    public StoreDto updateStore(@RequestBody StoreDto storePatch, @PathVariable UUID id) {
        return converter.convertToDto(updateStore.updateStore(findStore.findById(id), storePatch));
    }

    /**
     * Handle DELETE request to delete a store resource from the system
     *
     * @param id id of the resource to be deleted.
     * @return NO_CONTENT http status
     */
    @DeleteMapping(path = "/stores/{id}")
    public ResponseEntity<?> removeStore(@PathVariable UUID id) {
        removeStore.removeById(id);
        return ResponseEntity.noContent().build();
    }
}
