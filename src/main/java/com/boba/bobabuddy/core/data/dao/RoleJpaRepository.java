package com.boba.bobabuddy.core.data.dao;

import com.boba.bobabuddy.core.domain.Role;
import org.springframework.stereotype.Repository;
/**
 * DAO for Role resources
 */
@Repository
public interface RoleJpaRepository extends GenericResourceJpaRepository<Role, Long> {
    Role findByName(String role);
}
