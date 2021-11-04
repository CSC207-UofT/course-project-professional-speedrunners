package com.boba.bobabuddy.core.usecase.ratableobject;

import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.infrastructure.database.RatableObjectJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class FindRatable implements com.boba.bobabuddy.core.usecase.port.ratableport.IFindRatable {
    private final RatableObjectJpaRepository<RatableObject> repo;

    @Autowired
    public FindRatable(RatableObjectJpaRepository<RatableObject> repo) {
        this.repo = repo;
    }

    @Override
    public RatableObject findById(UUID id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such ratable", new Exception()));
    }
}
