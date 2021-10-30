package com.boba.bobabuddy.infrastructure.database;

import com.boba.bobabuddy.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID> {
}
