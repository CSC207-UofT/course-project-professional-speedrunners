package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.store.port.ICreateStore;
import com.boba.bobabuddy.core.usecase.store.port.IFindStore;
import com.boba.bobabuddy.core.usecase.store.port.IRemoveStore;
import com.boba.bobabuddy.core.usecase.store.port.IUpdateStore;
import com.boba.bobabuddy.infrastructure.assembler.StoreResourceAssembler;
import com.boba.bobabuddy.infrastructure.dto.converter.DtoConverter;
import com.boba.bobabuddy.infrastructure.dto.StoreDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


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
    private final DtoConverter<Store, StoreDto> dtoConverter;

    // Put response entities into a wrapper that stores hypermedia links (HATEOAS)
    private final StoreResourceAssembler assembler;

    @Autowired
    public StoreController(ICreateStore createStore, IRemoveStore removeStore, IUpdateStore updateStore,
                           IFindStore findStore, ModelMapper mapper, StoreResourceAssembler assembler) {
        this.createStore = createStore;
        this.findStore = findStore;
        this.removeStore = removeStore;
        this.updateStore = updateStore;
        this.assembler = assembler;

        this.dtoConverter = new DtoConverter<>(mapper, Store.class, StoreDto.class);
    }

    /***
     * Post HTTP requests for creating store resource
     * @param createStoreRequest Request class that contains data necessary to construct a Store entity.
     * @return Store that was constructed, which will be automatically converted to JSON and send it to the caller.
     */
    @PostMapping(path = "/stores", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<StoreDto>> createStore(@RequestBody StoreDto createStoreRequest) {
        StoreDto storeToPresent = dtoConverter.convertToDto(createStore.create(dtoConverter.convertToEntity(createStoreRequest)));
        return ResponseEntity.created(linkTo(methodOn(StoreController.class).findById(storeToPresent.getId())).toUri()).body(assembler.toModel(storeToPresent));
    }

    /***
     * Root endpoint for store resources
     * @return collection of store resources exist in the database
     */
    @GetMapping(path = "/stores")
    public ResponseEntity<CollectionModel<EntityModel<StoreDto>>> findAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findStore.findAll())));
    }

    /***
     * Handles GET requests for a store resource with matching id.
     * @param id the primary uuid key of the resource
     * @return the store resource with matching UUID
     */
    @GetMapping(path = "/stores/{id}")
    public ResponseEntity<EntityModel<StoreDto>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toModel(dtoConverter.convertToDto(findStore.findById(id))));
    }

    /**
     * Handles GET requests for a store resource that has a certain item
     * @param id id of the item resource
     * @return the store resource that has the specified item
     */
    @GetMapping(path = "/stores", params = "itemId")
    public ResponseEntity<EntityModel<StoreDto>> findByItem(@RequestParam("itemId") UUID id) {
        return ResponseEntity.ok(assembler.toModel(dtoConverter.convertToDto(findStore.findByItem(id))));

    }

    /***
     * Handles GET requests for store resources that have matching location.
     * @param location location of required store
     * @return collection of store resources that match the query.
     */
    @GetMapping(path = "/stores", params = "location")
    public ResponseEntity<CollectionModel<EntityModel<StoreDto>>> findByLocation(@RequestParam("location") String location) {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findStore.findByLocation(location))));
    }

    /***
     * Handles GET requests for store resources that have matching name
     * @param name name of required store
     * @return collection of store resources that match the query.
     */
    @GetMapping(path = "/stores", params = "name")
    public ResponseEntity<CollectionModel<EntityModel<StoreDto>>> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findStore.findByName(name))));
    }

    /***
     * Handles GET requests for store resources that partially matches the provided name
     * @param nameContain name to match for
     * @return collection of store resources that match the query.
     */
    @GetMapping(path = "/stores", params = "name-contain")
    public ResponseEntity<CollectionModel<EntityModel<StoreDto>>> findByNameContaining(@RequestParam("name-contain") String nameContain) {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findStore.findByNameContaining(nameContain))));
    }

    /***
     * Handles GET requests for store resources that have rating greater than or equal to a given value
     * @param rating the rating used for comparison
     * @return collection of store resources that match the query.
     */
    @GetMapping(path = "/stores", params = "rating-geq")
    public ResponseEntity<CollectionModel<EntityModel<StoreDto>>> findByAvgRatingGreaterThanEqual(@RequestParam("rating-geq") float rating,
                                                                                               @RequestParam(defaultValue = "false") boolean sorted) {
        return ResponseEntity.ok(assembler.toCollectionModel(dtoConverter.convertToDtoCollection(findStore.findByAvgRatingGreaterThanEqual(rating, sorted))));
    }

    /**
     * Handle GET request to find a store resource that contains a particular rating
     * @param id id of the rating
     * @return a store resource that match teh query
     */
    @GetMapping(path = "/stores", params = "ratingId")
    public ResponseEntity<EntityModel<StoreDto>> findByRating(@RequestParam("ratingId") UUID id) {
        return ResponseEntity.ok(assembler.toModel(dtoConverter.convertToDto(findStore.findByRating(id))));

    }

    /**
     * Handles PUT request to update an existing store resource
     * @param id store resource to be updated
     * @param storePatch the same store with updated fields.
     * @return the store resource after the modification
     */
    @PutMapping(path = "/stores/{id}")
    public ResponseEntity<EntityModel<StoreDto>> updateStore(@RequestBody StoreDto storePatch, @PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toModel(dtoConverter.convertToDto(updateStore.updateStore(findStore.findById(id), storePatch))));

    }

    /**
     * Handle DELETE request to delete a store resource from the system
     * @param id id of the resource to be deleted.
     * @return NO_CONTENT http status
     */
    @DeleteMapping(path = "/stores/{id}")
    public ResponseEntity<?> removeStore(@PathVariable UUID id) {
        removeStore.removeById(id);
        return ResponseEntity.noContent().build();
    }
}
