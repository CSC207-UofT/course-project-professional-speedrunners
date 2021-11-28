package com.boba.bobabuddy.core.usecase.category.impl;

import com.boba.bobabuddy.core.entity.Category;
import com.boba.bobabuddy.core.usecase.category.FindCategoryService;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.item.FindItem;
import com.boba.bobabuddy.infrastructure.dao.CategoryJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class FindCategoryServiceImpl implements FindCategoryService {
    final private CategoryJpaRepository repo;
    final private FindItem findItem;

    public FindCategoryServiceImpl(CategoryJpaRepository repo, FindItem findItem){
        this.repo = repo;
        this.findItem = findItem;
    }

    @Override
    public Category findByName(String name) throws ResourceNotFoundException {
        return repo.findByName(name);
    }
    @Override
    public Set<Category> findByItem(UUID id){
        return findItem.findById(id).getCategories();
    }

    @Override
    public List<Category> findAll(){
        return repo.findAll();
    }
}
