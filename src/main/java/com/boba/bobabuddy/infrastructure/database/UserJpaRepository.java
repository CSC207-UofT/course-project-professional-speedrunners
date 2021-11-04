package com.boba.bobabuddy.infrastructure.database;

import com.boba.bobabuddy.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    List<User> findByName(String name);

    User removeByEmail(String email);
}
