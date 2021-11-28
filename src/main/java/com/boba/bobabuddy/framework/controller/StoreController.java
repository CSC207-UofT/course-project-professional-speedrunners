package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.service.store.CreateStoreService;
import com.boba.bobabuddy.core.service.store.FindStoreService;
import com.boba.bobabuddy.core.service.store.RemoveStoreService;
import com.boba.bobabuddy.core.service.store.UpdateStoreService;
import com.boba.bobabuddy.framework.converter.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


/**
 * REST controller for Store related api calls
 */

@RestController
@RequiredArgsConstructor
@Component("StoreController")
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/admin/stores")
    public StoreDto createStore(@RequestBody StoreDto createStoreRequest) {
        return converter.convertToDto(createStore.create(createStoreRequest));

    }

    /**
     * Root endpoint for store resources
     *
     * @return collection of store resources exist in the database
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/stores")
    public List<StoreDto> findAll(@RequestParam(defaultValue = "unsorted") String sortBy) {
        return converter.convertToDtoList(findStore.findAll(SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for a store resource with matching id.
     *
     * @param id the primary uuid key of the resource
     * @return the store resource with matching UUID
     */
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/stores", params = "itemId")
    public StoreDto findByItem(@RequestParam("itemId") UUID id) {
        return converter.convertToDto(findStore.findByItem(id));

    }

    /**
     * Handles GET requests for store resources that have matching location.
     *
     * @param location location of required store
     * @param sortBy
     * @return collection of store resources that match the query.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/stores", params = "location")
    public List<StoreDto> findByLocation(@RequestParam("location") String location,
                                         @RequestParam(defaultValue = "unsorted") String sortBy) {
        return converter.convertToDtoList(findStore.findByLocation(location, SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for store resources that have matching name
     *
     * @param name name of required store
     * @param sortBy
     * @return collection of store resources that match the query.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/stores", params = "name")
    public List<StoreDto> findByName(@RequestParam("name") String name,
                                     @RequestParam(defaultValue = "unsorted") String sortBy) {
        return converter.convertToDtoList(findStore.findByName(name, SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for store resources that partially matches the provided name
     *
     * @param nameContain name to match for
     * @param sortBy
     * @return collection of store resources that match the query.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/stores", params = "name-contain")
    public List<StoreDto> findByNameContaining(@RequestParam("name-contain") String nameContain,
                                               @RequestParam(defaultValue = "unsorted") String sortBy) {
        return converter.convertToDtoList(findStore.findByNameContaining(nameContain, SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handles GET requests for store resources that have rating greater than or equal to a given value
     *
     * @param rating the rating used for comparison
     * @param sortBy
     * @return collection of store resources that match the query.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/stores", params = "rating-geq")
    public List<StoreDto> findByAvgRatingGreaterThanEqual(@RequestParam("rating-geq") float rating,
                                                          @RequestParam(defaultValue = "unsorted") String sortBy) {
        return converter.convertToDtoList(
                findStore.findByAvgRatingGreaterThanEqual(rating, SortQueryBuilder.buildSort(sortBy)));
    }

    /**
     * Handle GET request to find a store resource that contains a particular rating
     *
     * @param id id of the rating
     * @return a store resource that match teh query
     */
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/user/stores/{id}")
    @PreAuthorize("@StoreController.getFindStore().findById(#id).getOwner() == authentication.principal.username || hasAuthority('ROLE_ADMIN')")
    public StoreDto updateStore(@RequestBody StoreDto storePatch, @PathVariable UUID id) {
        return converter.convertToDto(updateStore.updateStore(findStore.findById(id), storePatch));
    }

    /**
     * Handle DELETE request to delete a store resource from the system
     *
     * @param id id of the resource to be deleted.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/stores/{id}")
    public void removeStore(@PathVariable UUID id) {
        removeStore.removeById(id);
    }

    public FindStoreService getFindStore() {
        return findStore;
    }
}
