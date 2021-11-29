package com.boba.bobabuddy.core.service.store.impl;

import com.boba.bobabuddy.core.data.dao.StoreJpaRepository;
import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.service.store.CreateStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class handle the usecase of creating store and adding it into the system.
 */

@Service("CreateStoreService")
@Transactional
@RequiredArgsConstructor
public class CreateStoreServiceImpl implements CreateStoreService {
    private final StoreJpaRepository repo;

    @Override
    public Store create(StoreDto store) {
        Store createdStore = Store.builder()
                .name(store.getName())
                .location(store.getLocation())
                .build();
        return repo.save(createdStore);
    }

}
