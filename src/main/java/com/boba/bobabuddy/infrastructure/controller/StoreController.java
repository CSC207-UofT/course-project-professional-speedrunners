package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.request.CreateStoreRequest;
import com.boba.bobabuddy.core.usecase.store.port.ICreateStore;
import com.boba.bobabuddy.core.usecase.store.port.IFindStore;
import com.boba.bobabuddy.core.usecase.store.port.IRemoveStore;
import com.boba.bobabuddy.core.usecase.store.port.IUpdateStore;
import com.boba.bobabuddy.infrastructure.assembler.StoreResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/***
 * REST controller for Store related api calls
 */

@RestController
public class StoreController {

    //fields for all Store usecase, with interface
    private final ICreateStore createStore;
    private final IRemoveStore removeStore;
    private final IUpdateStore updateStore;
    private final IFindStore findStore;
    private final StoreResourceAssembler assembler;

    @Autowired
    public StoreController(ICreateStore createStore, IRemoveStore removeStore, IUpdateStore updateStore,
                           IFindStore findStore, StoreResourceAssembler assembler) {
        this.createStore = createStore;
        this.findStore = findStore;
        this.removeStore = removeStore;
        this.updateStore = updateStore;
        this.assembler = assembler;
    }

    /***
     * add the Store resource into the database.
     * @param createStoreRequest Request class that contains data necessary to construct a Store entity.
     * @return Store that was constructed, which will be automatically converted to JSON and send it to the caller.
     */
    // Simply, a POST request called to the endpoint <domain address>/store/ will be mapped to this method.
    // the <produces> parameter decides how the return value will be converted to and be sent back to the caller,
    // in this case a JSON.
    // the @RequestBody annotation indicates that the body of an HTTP request will be interpreted as an POJO
    // representation by HTTPMessageConverter, which converts it to the parameter type (in this case createStoreRequest).
    // Then, it will be passed to the method.
    @PostMapping(path = "/stores", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Store>> createStore(@RequestBody CreateStoreRequest createStoreRequest) {
        Store storeToPresent = createStore.create(createStoreRequest.toStore());
        return ResponseEntity.ok(assembler.toModel(storeToPresent));
    }

    /***
     * query for a store resource with matching id.
     * @param id the primary uuid key of the resource
     * @return Store with matching UUID, which will be automatically converted to JSON and send it to the caller.
     */
    // Exceptions thrown in controller class will be handled automatically by SpringFramework
    @GetMapping(path = "/stores/{id}")
    public ResponseEntity<EntityModel<Store>> findById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(assembler.toModel(findStore.findById(id)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/stores", params = "itemId")
    public ResponseEntity<EntityModel<Store>> findByItem(@RequestParam("itemId") UUID id) {
        try {
            return ResponseEntity.ok(assembler.toModel(findStore.findByItem(id)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


    /***
     * Query for all exiting store resource
     * @return list of store resources exist in the database
     */
    @GetMapping(path = "/stores")
    public ResponseEntity<CollectionModel<EntityModel<Store>>> findAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(findStore.findAll()));
    }

    /***
     * query for store resources that have matching location.
     * @param location location of required store
     * @return A store resource that match the query.
     */
    @GetMapping(path = "/stores", params = "location")
    public ResponseEntity<CollectionModel<EntityModel<Store>>> findByLocation(@RequestParam("location") String location) {
        return ResponseEntity.ok(assembler.toCollectionModel(findStore.findByLocation(location)));
    }


    /***
     * query for store resources that have matching name.
     * @param name name of required store
     * @return A store resource that match the query.
     */
    @GetMapping(path = "/stores", params = "name")
    public ResponseEntity<CollectionModel<EntityModel<Store>>> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(assembler.toCollectionModel(findStore.findByName(name)));
    }

    /***
     * query for store resource that partially matches the provided name
     * @param nameContain infix of required store's name
     * @return list of store resources that match the query.
     */
    @GetMapping(path = "/stores", params = "name-contain")
    public ResponseEntity<CollectionModel<EntityModel<Store>>> findByNameContaining(@RequestParam("name-contain") String nameContain) {
        return ResponseEntity.ok(assembler.toCollectionModel(findStore.findByNameContaining(nameContain)));
    }

    /***
     * query for store resources that have rating greater than or equal to a given value
     * @param rating the rating used for comparison
     * @param sorted whether returned list should be sorted
     * @return list of store resources that match the query.
     */
    @GetMapping(path = "/stores", params = "rating-geq")
    public ResponseEntity<CollectionModel<EntityModel<Store>>> findByAvgRatingGreaterThanEqual(@RequestParam("rating-geq") float rating,
                                                                                               @RequestParam(defaultValue = "false") boolean sorted) {
        return ResponseEntity.ok(assembler.toCollectionModel(findStore.findByAvgRatingGreaterThanEqual(rating, sorted)));
    }

    /***
     * Update a store given its id by overwriting it.
     * @param id the primary uuid key of the resource
     * @return updated store
     */
    @PutMapping(path = "/stores/{id}")
    public ResponseEntity<EntityModel<Store>> updateStore(@RequestParam Store storePatch, @PathVariable UUID id) {
        try {
            return ResponseEntity.ok(assembler.toModel(updateStore.updateStore(findStore.findById(id), storePatch)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DifferentResourceException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }


    /***
     * Removes a store from database that has the matching storeId.
     * @param id the primary uuid key of the resource
     * @return Store that was removed from the database.
     */
    @DeleteMapping(path = "/stores/{id}")
    public ResponseEntity<?> removeStore(@PathVariable UUID id) {
        try {
            removeStore.removeById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/stores", params = "ratingId")
    public ResponseEntity<EntityModel<Store>> findByRating(@RequestParam("ratingId") UUID id) {
        try {
            return ResponseEntity.ok(assembler.toModel(findStore.findByRating(id)));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
