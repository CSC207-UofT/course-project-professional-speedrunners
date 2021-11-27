package com.boba.bobabuddy.core.service.item.impl;


import com.boba.bobabuddy.core.data.dao.ItemJpaRepository;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.item.FindItemService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * This class handle the usecase of finding items in the system.
 */
@Service
@Transactional
public class FindItemServiceImpl implements FindItemService {

    private final ItemJpaRepository repo;

    /**
     * Initialize FindItem usecase by injecting dependencies
     *
     * @param repo data access object for handling item data
     */
    public FindItemServiceImpl(final ItemJpaRepository repo) {
        this.repo = repo;
    }


    @Override
    public List<Item> findByStore(UUID id) {
        return repo.findByStore_id(id);
    }


    @Override
    public Item findById(UUID id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such Item"));
    }

    /*
     * TODO: review this code
     *  probably not a good idea if the database become large. Consider adding a pageable alternative to this
     *  and not expose this directly to the api.
     *  Refer to Spring Data JPA for detail
     */
    @Override
    public List<Item> findAll() {
        return repo.findAll();
    }


    @Override
    public List<Item> findByName(String name) {
        return repo.findByName(name);
    }


    @Override
    public List<Item> findByNameContaining(String name) {
        return repo.findByNameContaining(name);
    }


    @Override
    public List<Item> findByPriceLessThanEqual(float price, boolean sorted) {
        Sort sorter = ((sorted) ? Sort.by("price").ascending() : Sort.unsorted());
        return repo.findByPriceLessThanEqual(price, sorter);
    }


    @Override
    public List<Item> findByAvgRatingGreaterThanEqual(float avgRating, boolean sorted) throws IllegalArgumentException {
        if (avgRating > 1 || avgRating < 0) throw new IllegalArgumentException("avgRating must be between 0 and 1");
        Sort sorter = ((sorted) ? Sort.by("avgRating").descending() : Sort.unsorted());
        return repo.findByAvgRatingGreaterThanEqual(avgRating, sorter);
    }


    @Override
    public Item findByRating(UUID id) throws ResourceNotFoundException {
        return repo.findByRatings_id(id).orElseThrow(() -> new ResourceNotFoundException("No Item with the specified rating exist"));
    }
}

