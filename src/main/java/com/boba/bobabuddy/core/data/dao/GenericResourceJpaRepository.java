package com.boba.bobabuddy.core.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericResourceJpaRepository<T, D> extends JpaRepository<T, D> {
}
