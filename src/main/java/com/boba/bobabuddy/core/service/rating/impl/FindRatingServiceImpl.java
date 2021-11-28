package com.boba.bobabuddy.core.service.rating.impl;

import com.boba.bobabuddy.core.data.dao.RatingJpaRepository;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.item.FindItemService;
import com.boba.bobabuddy.core.service.rating.FindRatingService;
import com.boba.bobabuddy.core.service.store.FindStoreService;
import com.boba.bobabuddy.core.service.user.FindUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This is the usecase responsible for finding existing Rating entities in the system.
 */
@Service
@Transactional
public class FindRatingServiceImpl implements FindRatingService {

    private final RatingJpaRepository repo;
    private final FindItemService findItem;
    private final FindUserService findUser;
    private final FindStoreService findStore;

    /**
     * Constructor for the FindRating usecase.
     *
     * @param repo     the RatingJpaRepository to be searched for Rating entities
     * @param findUser
     */
    public FindRatingServiceImpl(RatingJpaRepository repo, FindItemService findItem, FindUserService findUser, FindStoreService findStore) {
        this.repo = repo;
        this.findItem = findItem;
        this.findUser = findUser;
        this.findStore = findStore;
    }


    @Override
    public Set<Rating> findByItem(UUID id) throws ResourceNotFoundException {
        return findItem.findById(id).getRatings();
    }


    @Override
    public Set<Rating> findByStore(UUID id) throws ResourceNotFoundException {
        return findStore.findById(id).getRatings();
    }


    @Override
    public Set<Rating> findByUser(String email) {
        return findUser.findByEmail(email).getRatings();
    }


    @Override
    public Rating findById(UUID id) throws ResourceNotFoundException {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such rating"));
    }


    @Override
    public List<Rating> findAll() {
        return repo.findAll();
    }
}
