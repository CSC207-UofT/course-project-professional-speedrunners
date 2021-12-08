package com.boba.bobabuddy.core.data.dao;

import com.boba.bobabuddy.core.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * DAO for User resources
 */
@Repository
public interface UserJpaRepository extends GenericResourceJpaRepository<User, UUID> {
    List<User> findByName(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findByRatings_id(UUID id);
}
