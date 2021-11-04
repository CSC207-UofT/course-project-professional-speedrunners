package com.boba.bobabuddy.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface GenericResourceJpaRepository<T, D> extends JpaRepository<T, D> {
    Optional<T> removeById(D id);
}
