package com.boba.bobabuddy.core.usecase.ratableobject;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.ratableobject.port.IFindRatable;
import com.boba.bobabuddy.infrastructure.dao.RatableObjectJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/***
 * Usecase for finding ratable objects in the database
 */
@Service
@Transactional
public class FindRatable implements IFindRatable {
    private final RatableObjectJpaRepository<RatableObject> repo;

    /***
     * Constructor to inject DAO dependency
     * @param repo the DAO for RatableObject
     */
    public FindRatable(RatableObjectJpaRepository<RatableObject> repo) {
        this.repo = repo;
    }

    /***
     * Find RatableObject by its uuid
     * @param id uuid of the item
     * @return the Item if it was found.
     * @throws ResourceNotFoundException Thrown when no item with matching ID exist in the database
     */
    @Override
    public RatableObject findById(UUID id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such ratable object", new Exception()));
    }
}
