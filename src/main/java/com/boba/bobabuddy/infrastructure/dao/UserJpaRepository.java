package com.boba.bobabuddy.infrastructure.dao;

import com.boba.bobabuddy.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID> {
    List<User> findByName(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findByRatings_id(UUID id);
}
