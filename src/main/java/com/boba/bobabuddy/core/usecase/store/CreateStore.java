package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.port.storeport.ICreateStore;
import com.boba.bobabuddy.infrastructure.database.StoreJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class handle the usecase of creating store and adding it into the system.
 * It implements the ICreateStore interface which defines what operations are supported by this usecase object
 * from a controller's perspective.
 */

// Springframework annotations that marks this class as a service component
// Functionally identical to the @Component annotation as far as I'm aware, and it essentially
// registers the class as a component (bean) so that Spring can automatically configure and inject dependencies
// as needed.
@Service
// Indicates that operations performed in this class in Transactional.
// Refers to this link for more info: https://java.christmas/2019/24
@Transactional
public class CreateStore implements ICreateStore {
    //JPA repository port - Handles queries and update, creation, deletion of entries in the database
    private final StoreJpaRepository repo;

    /**
     * Initialize the Create Store usecase by injecting it with required dependencies.
     * @param repo a database object for handling item data
     */
    // Spring annotation that instruct springboot to attempt to automatically inject dependencies as needed.
    @Autowired
    public CreateStore(StoreJpaRepository repo) {
        this.repo = repo;
    }

    /***
     * Create an store and save it to the database
     * Note that the actual building of a Store object is handled by the CreateStoreRequest
     * wrapper which help with converting JSON request into Entity objects via HTTPMessageConverter.
     * @param store Store to be persisted in the database.
     * @return the Store object that was persisted in the database.
     */
    @Override
    public Store create(Store store) {
        return repo.save(store);
    }

}
