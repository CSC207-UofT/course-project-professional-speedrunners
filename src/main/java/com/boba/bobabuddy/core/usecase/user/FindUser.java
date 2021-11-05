package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.port.userport.IFindUser;
import com.boba.bobabuddy.infrastructure.database.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FindUser implements IFindUser {

    private final UserJpaRepository repo;

    @Autowired
    public FindUser(UserJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public User findByEmail(String email) throws ResourceNotFoundException {
        return repo.findById(email).orElseThrow(() -> new ResourceNotFoundException("user not found", new Exception()));
    }

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public List<User> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public boolean userExistanceCheck(String email) {
        return repo.findById(email).isPresent();
    }
}
