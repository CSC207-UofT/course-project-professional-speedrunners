package com.boba.bobabuddy.core.service.category.impl;

import com.boba.bobabuddy.core.data.dao.CategoryJpaRepository;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.category.FindCategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * This class handle the usecase of finding categories in the system.
 */

@Service("FindCategoryService")
@Transactional
public class FindCategoryServiceImpl implements FindCategoryService {
    final private CategoryJpaRepository repo;

    public FindCategoryServiceImpl(CategoryJpaRepository repo){
        this.repo = repo;
    }

    @Override
    public Category findByName(String name) throws ResourceNotFoundException {
        return repo.findByNameIgnoringCase(name);
    }

    @Override
    public List<Category> findByNameContaining(String name, Sort sort){
        return repo.findByNameContainingIgnoreCase(name, sort);
    }

    @Override
    public List<Category> findByItem(UUID id, Sort sort) throws ResourceNotFoundException{
        return repo.findByItems_id(id, sort);
    }

    @Override
    public List<Category> findAll(Sort sort){
        return repo.findAll();
    }

    @Override
    public Category findById(UUID id) throws ResourceNotFoundException{
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such Category"));
    }
}
