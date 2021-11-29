package com.boba.bobabuddy.core.usecase.category.impl;

import com.boba.bobabuddy.core.entity.Category;
import com.boba.bobabuddy.core.usecase.category.FindCategoryService;
import com.boba.bobabuddy.core.usecase.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.usecase.item.FindItem;
import com.boba.bobabuddy.infrastructure.dao.CategoryJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This class handle the usecase of finding categories in the system.
 */

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
    public Set<Category> findByItem(UUID id) throws ResourceNotFoundException{
        return findItem.findById(id).getCategories();
    }

    @Override
    public List<Category> findAll(Sort sort){
        return repo.findAll();
    }

    @Override
    public Category findById(UUID id) throws ResourceNotFoundException{
        //TODO: figure this thing out
        return repo.findById(id);
    }
}
