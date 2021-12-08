package com.boba.bobabuddy.core.service.category;

import com.boba.bobabuddy.core.data.dao.CategoryJpaRepository;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.exceptions.ResourceNotFoundException;
import com.boba.bobabuddy.core.service.category.impl.RemoveCategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoveCategoryImplTest {
    @Mock
    private CategoryJpaRepository repo;

    @Mock
    private FindCategoryService findCategoryService;

    @InjectMocks
    private RemoveCategoryServiceImpl removeCategory;

    @Test
    void restRemove() throws ResourceNotFoundException{
        Category category = new Category();
         UUID catId = UUID.randomUUID();

         category.setId(catId);

         when(findCategoryService.findById(catId)).thenReturn(category);

         removeCategory.removeById(catId);
         verify(repo, Mockito.times(1)).delete(category);

    }
}
