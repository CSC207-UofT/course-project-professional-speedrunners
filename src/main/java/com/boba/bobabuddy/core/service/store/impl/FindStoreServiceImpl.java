package com.boba.bobabuddy.core.service.store.impl;

import com.boba.bobabuddy.core.data.dao.StoreJpaRepository;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.item.FindItemService;
import com.boba.bobabuddy.core.service.store.FindStoreService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * This class handle the usecase of finding stores in the system.
 */
@Service
@Transactional
public class FindStoreServiceImpl implements FindStoreService {
    private final StoreJpaRepository repo;
    private final FindItemService findItem;

    /**
     * Initialize FindStore usecase by injecting dependencies
     *
     * @param repo     database object for handling item data
     * @param findItem FindItem usecase
     */
    public FindStoreServiceImpl(final StoreJpaRepository repo, FindItemService findItem) {
        this.repo = repo;
        this.findItem = findItem;
    }

    @Override
    public List<Store> findAll() {
        return repo.findAll();
    }


    @Override
    public List<Store> findByLocation(String location) {
        return repo.findByLocation(location);
    }

    @Override
    public Store findById(UUID id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such store", new Exception()));
    }

    @Override
    public List<Store> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public List<Store> findByNameContaining(String name) {
        return repo.findByNameContaining(name);
    }

    @Override
    public List<Store> findByAvgRatingGreaterThanEqual(float avgRating, boolean sorted) {
        if (0 <= avgRating && avgRating <= 1) {
            Sort sorter = ((sorted) ? Sort.by("avgRating").descending() : Sort.unsorted());
            return repo.findByAvgRatingGreaterThanEqual(avgRating, sorter);
        }
        throw new IllegalArgumentException("avgRating must be between 0 and 1");
    }

    @Override
    public Store findByItem(UUID id) throws ResourceNotFoundException {
        return findItem.findById(id).getStore();
    }

    @Override
    public Store findByRating(UUID id) throws ResourceNotFoundException {
        return repo.findByRatings_id(id).orElseThrow(() -> new ResourceNotFoundException("No Store with the specified rating exist"));
    }
}

