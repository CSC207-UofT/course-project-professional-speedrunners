package com.boba.bobabuddy.core.service.category.impl;

import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.category.FindCategoryService;
import com.boba.bobabuddy.core.service.item.impl.FindItemServiceImpl;
import com.boba.bobabuddy.core.data.dao.CategoryJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This class handle the usecase of finding categories in the system.
 */

@Service("FindCategoryService")
@Transactional
public class FindCategoryServiceImpl implements FindCategoryService {
    final private CategoryJpaRepository repo;
    final private FindItemServiceImpl findItem;

    public FindCategoryServiceImpl(CategoryJpaRepository repo, FindItemServiceImpl findItem){
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
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such Category"));
    }
}
