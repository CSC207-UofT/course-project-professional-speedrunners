package com.boba.bobabuddy.core.service.ratableobject.impl;

import com.boba.bobabuddy.core.data.dao.RatableObjectJpaRepository;
import com.boba.bobabuddy.core.domain.RatableObject;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.ratableobject.FindRatableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/***
 * Usecase for finding ratable objects in the database
 */
@Service
@Transactional
@RequiredArgsConstructor
public class FindRatableServiceImpl implements FindRatableService {

    private final RatableObjectJpaRepository<RatableObject> repo;

    @Override
    public RatableObject findById(UUID id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such ratable object"));
    }

    @Override
    public RatableObject findByRating(UUID id) {
        return repo.findByRatings_id(id)
                .orElseThrow(() -> new ResourceNotFoundException("No ratable with the specified rating exist."));
    }

    @Override
    public RatableObject findByTypeAndId(String type, UUID id) {
        return repo.findByTypeAndId(type, id).orElseThrow(() -> new ResourceNotFoundException(type + " not found."));
    }

}
