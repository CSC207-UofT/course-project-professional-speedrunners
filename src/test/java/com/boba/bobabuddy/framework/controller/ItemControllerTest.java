package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.service.item.CreateItemService;
import com.boba.bobabuddy.core.service.item.FindItemService;
import com.boba.bobabuddy.core.service.item.RemoveItemService;
import com.boba.bobabuddy.core.service.item.UpdateItemService;
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
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//integration testing without core and persistence layer
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ItemController.class,
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class) },
        excludeAutoConfiguration = { SecurityAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class ItemControllerTest {
    @MockBean
    private CreateItemService createItem;
    @MockBean
    private RemoveItemService removeItem;
    @MockBean
    private UpdateItemService updateItem;
    @MockBean
    private FindItemService findItem;
    @MockBean
    private DtoConverter<Item, ItemDto> converter;


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;


    private Store store1;
    private StoreDto storeDto1;
    private UUID storeId, id1, id2, id3, ratingId;
    private ItemDto createItemRequest;
    private Item item1, item2, item3;
    private ItemDto itemDto1, itemDto2, itemDto3;
    private List<Item> allItem, itemPrice, itemRating;
    private List<ItemDto> allItemDto, itemPriceDto, itemRatingDto;
    private Rating rating;

    @BeforeEach
    void setup() {
        storeId = UUID.randomUUID();
        store1 = Store.builder()
                .name("bob")
                .location("queens")
                .id(storeId)
                .build();
        storeDto1 = StoreDto.builder()
                .name("bob")
                .location("queens")
                .id(storeId)
                .build();

        createItemRequest = ItemDto.builder().name("tea").price(1).build();

        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();
        id3 = UUID.randomUUID();
        ratingId = UUID.randomUUID();


        rating = Rating.builder().rating(1).id(ratingId).build();

        item1 = Item.builder()
                .price(5)
                .store(store1)
                .name("milk tea")
                .id(id1)
                .build();
        itemDto1 = ItemDto.builder()
                .price(5)
                .store(storeDto1)
                .name("milk tea")
                .id(id1)
                .build();

        item2 = Item.builder()
                .price(7)
                .store(store1)
                .name("fruit tea")
                .id(id2)
                .build();

        itemDto2 = ItemDto.builder()
                .price(7)
                .store(storeDto1)
                .name("fruit tea")
                .id(id2)
                .build();


        item3 = Item.builder()
                .price(9)
                .store(store1)
                .name("bubble tea")
                .id(id3)
                .avgRating(0.5)
                .build();

        itemDto3 = ItemDto.builder()
                .price(9)
                .store(storeDto1)
                .name("bubble tea")
                .id(id3)
                .avgRating(0.5)
                .build();


        allItem = Arrays.asList(item1, item2, item3);
        allItemDto = Arrays.asList(itemDto1, itemDto2, itemDto3);
        itemPrice = Arrays.asList(item1, item2);
        itemPriceDto = Arrays.asList(itemDto1, itemDto2);
        itemRating = Arrays.asList(item2, item3);
        itemRatingDto = Arrays.asList(itemDto2, itemDto3);

    }

    @AfterEach
    void teardown() {

        store1 = null;
        storeId = null;

        createItemRequest = null;

        id1 = id2 = id3 = ratingId = null;

        rating = null;

        item1 = item2 = item3 = null;

        allItem = itemPrice = itemRating = null;

    }

    @Test
    void testCreateItem() throws Exception {

        ItemDto raw = ItemDto.builder()
                .price(1)
                .name("tea")
                .build();

        Item createdItem = Item.builder()
                .price(1)
                .id(id1)
                .store(store1)
                .name("tea")
                .build();


        ItemDto createItemDto = ItemDto.builder()
                .price(1)
                .id(id1)
                .store(storeDto1)
                .name("tea")
                .build();

        when(createItem.create(ArgumentMatchers.isA(ItemDto.class), eq(storeId))).thenReturn(createdItem);
        when(converter.convertToDto(ArgumentMatchers.isA(Item.class))).thenReturn(createItemDto);

        mockMvc.perform(post("/stores/{storeId}/items", storeId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createItemRequest)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("tea")))
                .andExpect(jsonPath("$.price", is(1.0)))
                .andExpect(jsonPath("$.store.name", is("bob")))
                .andExpect(jsonPath("$.store.location", is("queens")))
                .andExpect(jsonPath("$.store.id", is(storeId.toString())));
    }

    @Test
    void testFindAll() throws Exception {

        when(findItem.findAll(ArgumentMatchers.any())).thenReturn(allItem);
        when(converter.convertToDtoList(ArgumentMatchers.any())).thenReturn(allItemDto);

        mockMvc.perform(get("/items"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("milk tea")))
                .andExpect(jsonPath("$[0].price", is(5.0)))
                .andExpect(jsonPath("$[1].id", is(id2.toString())))
                .andExpect(jsonPath("$[1].name", is("fruit tea")))
                .andExpect(jsonPath("$[1].price", is(7.0)))
                .andExpect(jsonPath("$[2].id", is(id3.toString())))
                .andExpect(jsonPath("$[2].name", is("bubble tea")))
                .andExpect(jsonPath("$[2].price", is(9.0)));
    }

    @Test
    void testFindById() throws Exception {
        when(findItem.findById(id1)).thenReturn(item1);
        when(converter.convertToDto(ArgumentMatchers.any())).thenReturn(itemDto1);

        mockMvc.perform(get("/items/{id}", id1.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("milk tea")))
                .andExpect(jsonPath("$.price", is(5.0)))
                .andExpect(jsonPath("$.store.name", is("bob")))
                .andExpect(jsonPath("$.store.location", is("queens")))
                .andExpect(jsonPath("$.store.id", is(storeId.toString())));

    }

    @Test
    void testFindByName() throws Exception {

        when(findItem.findByName(eq("milk tea"), ArgumentMatchers.any())).thenReturn(List.of(item1));
        when(converter.convertToDtoList(ArgumentMatchers.any())).thenReturn(List.of(itemDto1));

        mockMvc.perform(get("/items/?name=milk tea"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("milk tea")))
                .andExpect(jsonPath("$[0].price", is(5.0)))
                .andExpect(jsonPath("$[0].store.name", is("bob")))
                .andExpect(jsonPath("$[0].store.location", is("queens")))
                .andExpect(jsonPath("$[0].store.id", is(storeId.toString())));

    }

    @Test
    void testFindByNameContaining() throws Exception {
        when(findItem.findByNameContaining(eq("tea"), ArgumentMatchers.any())).thenReturn(allItem);
        when(converter.convertToDtoList(ArgumentMatchers.any())).thenReturn(allItemDto);

        mockMvc.perform(get("/items/?name-contain=tea"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("milk tea")))
                .andExpect(jsonPath("$[0].price", is(5.0)))
                .andExpect(jsonPath("$[1].id", is(id2.toString())))
                .andExpect(jsonPath("$[1].name", is("fruit tea")))
                .andExpect(jsonPath("$[1].price", is(7.0)))
                .andExpect(jsonPath("$[2].id", is(id3.toString())))
                .andExpect(jsonPath("$[2].name", is("bubble tea")))
                .andExpect(jsonPath("$[2].price", is(9.0)));
    }

    @Test
    void testFindByStore() throws Exception {

        when(findItem.findByStore(eq(storeId), ArgumentMatchers.any())).thenReturn(allItem);
        when(converter.convertToDtoList(ArgumentMatchers.any())).thenReturn(allItemDto);

        mockMvc.perform(get("/stores/{storeId}/items", storeId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("milk tea")))
                .andExpect(jsonPath("$[0].price", is(5.0)))
                .andExpect(jsonPath("$[1].id", is(id2.toString())))
                .andExpect(jsonPath("$[1].name", is("fruit tea")))
                .andExpect(jsonPath("$[1].price", is(7.0)))
                .andExpect(jsonPath("$[2].id", is(id3.toString())))
                .andExpect(jsonPath("$[2].name", is("bubble tea")))
                .andExpect(jsonPath("$[2].price", is(9.0)));

    }

    @Test
    void testFindByPriceLessThanEqual() throws Exception {
        when(findItem.findByPriceLessThanEqual(eq(8), ArgumentMatchers.any())).thenReturn(itemPrice);
        when(converter.convertToDtoList(ArgumentMatchers.any())).thenReturn(itemPriceDto);

        mockMvc.perform(get("/items/?price-leq=8&sorted=true", storeId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("milk tea")))
                .andExpect(jsonPath("$[0].price", is(5.0)))
                .andExpect(jsonPath("$[1].id", is(id2.toString())))
                .andExpect(jsonPath("$[1].name", is("fruit tea")))
                .andExpect(jsonPath("$[1].price", is(7.0)));

    }

    @Test
    void testFindByAvgRatingGreaterThanEqual() throws Exception {
        when(findItem.findByAvgRatingGreaterThanEqual(eq(0.3), ArgumentMatchers.any())).thenReturn(itemRating);
        when(converter.convertToDtoList(ArgumentMatchers.any())).thenReturn(itemRatingDto);

        mockMvc.perform(get("/items/?rating-geq=0.3", storeId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))


                .andExpect(jsonPath("$[0].id", is(id2.toString())))
                .andExpect(jsonPath("$[0].name", is("fruit tea")))
                .andExpect(jsonPath("$[0].price", is(7.0)))
                .andExpect(jsonPath("$[1].id", is(id3.toString())))
                .andExpect(jsonPath("$[1].name", is("bubble tea")))
                .andExpect(jsonPath("$[1].price", is(9.0)));
    }

    @Test
    void testFindByRating() throws Exception {
        when(findItem.findByRating(ratingId)).thenReturn(item2);
        when(converter.convertToDto(ArgumentMatchers.any())).thenReturn(itemDto2);


        mockMvc.perform(get("/items/?ratingId={ratingId}", ratingId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id2.toString())))
                .andExpect(jsonPath("$.name", is("fruit tea")))
                .andExpect(jsonPath("$.price", is(7.0)));
    }

    @Test
    void testUpdateItem() throws Exception {
        ItemDto item4 = ItemDto.builder()
                .name("fruit tea")
                .price(7)
                .avgRating(0)
                .id(id1)
                .build();

        item2.setId(id1);

        when(updateItem.updateItem(eq(id1), isA(ItemDto.class))).thenReturn(item2);
        when(findItem.findById(id1)).thenReturn(item1);
        when(converter.convertToDto(ArgumentMatchers.any())).thenReturn(item4);


        mockMvc.perform(put("/items/{id}", id1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(item4)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("fruit tea")))
                .andExpect(jsonPath("$.price", is(7.0)));
    }


}
