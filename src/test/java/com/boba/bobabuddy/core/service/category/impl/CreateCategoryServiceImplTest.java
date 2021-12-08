package com.boba.bobabuddy.core.service.category.impl;

import com.boba.bobabuddy.core.data.dao.CategoryJpaRepository;
import com.boba.bobabuddy.core.data.dto.CategoryDto;
import com.boba.bobabuddy.core.domain.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCategoryServiceImplTest {

    @Mock
    CategoryJpaRepository repo;

    @InjectMocks
    CreateCategoryServiceImpl createCategoryService;

    @Test
    void create() {
        String name = "Milk tea";
        UUID id = UUID.randomUUID();

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName(name);

        Category category = new Category();
        category.setName(name);
        category.setId(id);

        when(repo.save(any())).thenReturn(category);

        Category returnedCategory = createCategoryService.create(categoryDto);

        assertNotNull(returnedCategory);
        assertEquals(name, returnedCategory.getName());
        assertEquals(id, returnedCategory.getId());
    }
}