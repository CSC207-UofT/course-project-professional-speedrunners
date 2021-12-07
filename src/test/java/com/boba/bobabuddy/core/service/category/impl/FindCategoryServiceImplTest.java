package com.boba.bobabuddy.core.service.category.impl;

import com.boba.bobabuddy.core.data.dao.CategoryJpaRepository;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCategoryServiceImplTest {
    @Mock
    CategoryJpaRepository repo;

    @Mock
    Sort sort;

    @InjectMocks
    FindCategoryServiceImpl findCategoryService;

    private Item item1, item2;
    private Category cat1, cat2, cat3;
    private UUID itemId1, itemId2, catId1, catId2, catId3;
    private List<Category> nameContainLst, itemLst, allLst;

    @BeforeEach
    void setUp() {
        item1 = new Item();
        item2 = new Item();
        itemId1 = UUID.randomUUID();
        itemId2 = UUID.randomUUID();
        item1.setId(itemId1);
        item2.setId(itemId2);

        cat1 = new Category();
        cat2 = new Category();
        cat3 = new Category();
        cat1.setName("Milk tea");
        cat2.setName("Tapioca");
        cat3.setName("Jasmine tea");
        catId1 = UUID.randomUUID();
        catId2 = UUID.randomUUID();
        catId3 = UUID.randomUUID();
        cat1.setId(catId1);
        cat2.setId(catId2);
        cat3.setId(catId3);

        cat1.setItems(Set.of(item1, item2));
        cat2.setItems(Set.of(item1));

        item1.setCategories(Set.of(cat1));
        item2.setCategories(Set.of(cat1, cat2));

        nameContainLst = List.of(cat1, cat3);
        itemLst = List.of(cat1, cat2);
        allLst = List.of(cat1, cat2, cat3);
    }

    @AfterEach
    void tearDown(){
        item1 = null;
        item2 = null;

        cat1 = null;
        cat2 = null;
        cat3 = null;

        nameContainLst = null;
        itemLst = null;
        allLst = null;
    }

    @Test
    void findByName() {
        String name = "Milk tea";
        when(repo.findByNameIgnoringCase(name)).thenReturn(cat1);
        Category returnedCategory = findCategoryService.findByName(name);

        assertNotNull(returnedCategory);
        assertEquals(cat1, returnedCategory);
    }

    @Test
    void findByNameContaining() {
        when(repo.findByNameContainingIgnoreCase("tea", sort)).thenReturn(nameContainLst);
        List<Category> returnedCategory = findCategoryService.findByNameContaining("tea", sort);

        assertNotNull(returnedCategory);
        assertEquals(nameContainLst, returnedCategory);
    }

    @Test
    void findByItem() {
        when(repo.findByItems_id(itemId2, sort)).thenReturn(itemLst);
        List<Category> returnedCategory = findCategoryService.findByItem(itemId2, sort);

        assertNotNull(returnedCategory);
        assertEquals(itemLst, returnedCategory);
    }

    @Test
    void findAll() {
        when(repo.findAll()).thenReturn(allLst);
        List<Category> returnedCategory = findCategoryService.findAll(sort);

        assertNotNull(returnedCategory);
        assertEquals(allLst, returnedCategory);
    }

    @Test
    void findById() {
        when(repo.findById(catId3)).thenReturn(java.util.Optional.ofNullable(cat3));
        Category returnedCategory = findCategoryService.findById(catId3);

        assertNotNull(returnedCategory);
        assertEquals(cat3, returnedCategory);
    }
}