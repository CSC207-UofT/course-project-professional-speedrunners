package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.data.dto.CategoryDto;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.service.category.*;
import com.boba.bobabuddy.framework.util.DtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoryController.class,
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class) },
        excludeAutoConfiguration = { SecurityAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {
    @MockBean
    private CreateCategoryService createCategoryService;

    @MockBean
    private FindCategoryService findCategoryService;

    @MockBean
    private RemoveCategoryService removeCategoryService;

    @MockBean
    private DtoConverter<Category, CategoryDto> converter;

    @MockBean
    private Sort sort;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private UUID id1, id2, id3, itemId;
    private Category cat1, cat2, cat3;
    private CategoryDto catDto1, catDto2, catDto3;
    private Item item;
    private List<Category> allLst, itemLst;
    private List<CategoryDto> allLstDto, itemLstDto;


    @BeforeEach
    void setUp() {
        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();
        id3 = UUID.randomUUID();
        itemId = UUID.randomUUID();

        cat1 = Category.builder()
                .name("milk tea")
                .id(id1).build();
        catDto1 = CategoryDto.builder()
                .name("milk tea")
                .id(id1).build();

        cat2 = Category.builder()
                .name("tea")
                .id(id2).build();
        catDto2 = CategoryDto.builder()
                .name("tea")
                .id(id2).build();

        cat3 = Category.builder()
                .name("bleh")
                .id(id3).build();
        catDto3 = CategoryDto.builder()
                .name("bleh")
                .id(id3).build();

        item = Item.builder()
                .name("blah")
                .id(itemId).build();

        cat1.addItem(item);
        cat2.addItem(item);
        item.addCategory(cat1);
        item.addCategory(cat2);

        allLst = List.of(cat1, cat2, cat3);
        allLstDto = List.of(catDto1, catDto2, catDto3);
        itemLst = List.of(cat1, cat2);
        itemLstDto = List.of(catDto1, catDto2);
    }

    @AfterEach
    void tearDown() {
        id1 = null;
        id2 = null;
        id3 = null;
        itemId = null;

        cat1 = null;
        cat2 = null;
        cat3 = null;

        item = null;

        allLst = null;
        itemLst = null;
    }

    @Test
    void createCategory() throws Exception {
        when(createCategoryService.create(ArgumentMatchers.any())).thenReturn(cat1);
        when(converter.convertToDto(ArgumentMatchers.any())).thenReturn(catDto1);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(catDto1)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("milk tea")));
    }

    @Test
    void findAll() throws Exception {
        when(findCategoryService.findAll(ArgumentMatchers.any())).thenReturn(allLst);
        when(converter.convertToDtoList(ArgumentMatchers.any())).thenReturn(allLstDto);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("milk tea")))
                .andExpect(jsonPath("$[1].id", is(id2.toString())))
                .andExpect(jsonPath("$[1].name", is("tea")))
                .andExpect(jsonPath("$[2].id", is(id3.toString())))
                .andExpect(jsonPath("$[2].name", is("bleh")));
    }

    @Test
    void findById() throws Exception {
        when(findCategoryService.findById(id1)).thenReturn(cat1);
        when(converter.convertToDto(ArgumentMatchers.any())).thenReturn(catDto1);

        mockMvc.perform(get("/categories/{id}", id1.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("milk tea")));
    }

    @Test
    void findByItem() throws Exception {
        when(findCategoryService.findByItem(eq(itemId), eq(sort))).thenReturn(itemLst);
        when(converter.convertToDtoList(any())).thenReturn(itemLstDto);

        mockMvc.perform(get("/categories/?itemId={itemId}", itemId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("milk tea")))
                .andExpect(jsonPath("$[1].id", is(id2.toString())))
                .andExpect(jsonPath("$[1].name", is("tea")));
    }

    @Test
    void findByName() throws Exception {
        when(findCategoryService.findByName(eq("milk tea"))).thenReturn(cat1);
        when(converter.convertToDto(ArgumentMatchers.any())).thenReturn(catDto1);

        mockMvc.perform(get("/categories/?name=milk tea"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("milk tea")));
    }

    @Test
    void findByNameContaining() throws Exception {
        when(findCategoryService.findByNameContaining(eq("tea"), any())).thenReturn(itemLst);
        when(converter.convertToDtoList(any())).thenReturn(itemLstDto);

        mockMvc.perform(get("/categories/?name-contain=tea"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("milk tea")))
                .andExpect(jsonPath("$[1].id", is(id2.toString())))
                .andExpect(jsonPath("$[1].name", is("tea")));
    }
}