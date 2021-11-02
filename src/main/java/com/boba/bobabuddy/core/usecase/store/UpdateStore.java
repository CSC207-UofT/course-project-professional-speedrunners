package com.boba.bobabuddy.core.usecase.store;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.store.exceptions.NoSuchStoreException;
import com.boba.bobabuddy.core.usecase.port.storeport.IUpdateStore;
import com.boba.bobabuddy.infrastructure.database.StoreJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class handle the usecase of updating stores in the system.
 * It implements the IFindStore interface which defines what operations are supported by the usecase object
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
public class UpdateStore implements IUpdateStore{
    private final StoreJpaRepository repo;

    /***
      Construct the usecase class
      @param repo the repository that hosts the Store entity.
     */

    @Autowired
    public UpdateStore(final StoreJpaRepository repo){
        this.repo = repo;
    }

    /***
     * Update a store by overwriting it.
     * The api user is responsible for sending in a Store representation that was modified.
     * However, if no Store with the same uuid exist and exception will be thrown.
     * TODO: properly implement the exception
     * @param store Store to update.
     * @return the updated item.
     * @throws NoSuchStoreException thrown when teh param store does not exist in the database.
     */

    @Override
    public Store updateStore(Store store) throws NoSuchStoreException{
        if (repo.existsById(store.getId())) return repo.save(store);
        throw new NoSuchStoreException("No such store", new Exception());
    }
}
