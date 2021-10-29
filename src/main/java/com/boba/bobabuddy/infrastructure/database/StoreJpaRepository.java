package com.boba.bobabuddy.infrastructure.database;

import com.boba.bobabuddy.core.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoreJpaRepository extends JpaRepository<Store, UUID> {
}
