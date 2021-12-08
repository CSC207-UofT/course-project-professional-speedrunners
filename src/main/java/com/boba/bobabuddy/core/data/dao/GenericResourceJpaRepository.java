package com.boba.bobabuddy.core.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
/**
 * DAO for generic type.
 */
@NoRepositoryBean
public interface GenericResourceJpaRepository<T, D> extends JpaRepository<T, D> {
}
