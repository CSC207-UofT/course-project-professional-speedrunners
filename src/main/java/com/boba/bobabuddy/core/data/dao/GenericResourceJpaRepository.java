package com.boba.bobabuddy.core.data.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericResourceJpaRepository<T, D> extends JpaRepository<T, D> {
}
