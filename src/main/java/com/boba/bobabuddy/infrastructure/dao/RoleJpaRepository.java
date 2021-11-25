package com.boba.bobabuddy.infrastructure.dao;

import com.boba.bobabuddy.core.entity.Role;

public interface RoleJpaRepository extends GenericResourceJpaRepository<Role, Long>{
    Role findByName(String role);
}
