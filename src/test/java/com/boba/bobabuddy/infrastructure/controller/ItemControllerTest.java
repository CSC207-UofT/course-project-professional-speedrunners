package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.item.port.ICreateItem;
import com.boba.bobabuddy.core.usecase.item.port.IFindItem;
import com.boba.bobabuddy.core.usecase.item.port.IRemoveItem;
import com.boba.bobabuddy.core.usecase.item.port.IUpdateItem;
import com.boba.bobabuddy.infrastructure.assembler.ItemResourceAssembler;
import com.boba.bobabuddy.infrastructure.dto.ItemDto;
import com.boba.bobabuddy.infrastructure.dto.SimpleItemDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ItemController.class)
@Import({ItemResourceAssembler.class, ModelMapper.class})
@AutoConfigureMockMvc
public class ItemControllerTest {
    @MockBean
    private ICreateItem createItem;
    @MockBean
    private IRemoveItem removeItem;
    @MockBean
    private IUpdateItem updateItem;
    @MockBean
    private IFindItem findItem;

    @Autowired
    private ItemResourceAssembler assembler;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ModelMapper modelMapper;


    private Store store1;
    private UUID storeId, id1, id2, id3, ratingId;
    private SimpleItemDto createItemRequest;
    private Item item1, item2, item3;
    private List<Item> allItem, itemPrice, itemRating;
    private Rating rating;

    @BeforeEach
    void setup() {

        store1 = new Store("bob", "queens");
        storeId = UUID.randomUUID();
        store1.setId(storeId);

        createItemRequest = new SimpleItemDto();
        createItemRequest.setName("tea");
        createItemRequest.setPrice(1);

        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();
        id3 = UUID.randomUUID();
        ratingId = UUID.randomUUID();


        rating = new Rating(1, null, item2);
        rating.setId(ratingId);

        item1 = new Item(5, store1, "milk tea");
        item1.setId(id1);
        item2 = new Item(7, store1, "fruit tea");
        item2.setId(id2);

        item2.addRating(rating);
        item3 = new Item(9, store1, "bubble tea");
        item3.setId(id3);
        item3.setAvgRating(0.5f);

        allItem = Arrays.asList(item1, item2, item3);
        itemPrice = Arrays.asList(item1, item2);
        itemRating = Arrays.asList(item2, item3);

    }

    @AfterEach
    void teardown() {

        store1 = null;
        storeId = null;

        createItemRequest = null;

        id1 = id2 = id3 = ratingId = null;

        rating = null;

        item1 = item2 = item3 =null;

        allItem =itemPrice =itemRating = null;

    }

    @Test
    void testCreateItem() throws Exception {

        Item raw = new Item(1, null, "tea");
        Item createdItem = new Item(1, null, "tea");
        createdItem.setId(id1);
        createdItem.setStore(store1);

        when(createItem.create(ArgumentMatchers.isA(Item.class), eq(storeId))).thenReturn(createdItem);

        mockMvc.perform(post("/stores/{storeId}/items", storeId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createItemRequest)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("tea")))
                .andExpect(jsonPath("$.price", is(1.0)))
                .andExpect(jsonPath("$.store.name", is("bob")))
                .andExpect(jsonPath("$.store.location", is("queens")))
                .andExpect(jsonPath("$.store.id", is(storeId.toString())));
    }

    @Test
    void testFindAll() throws Exception {

        when(findItem.findAll()).thenReturn(allItem);

        mockMvc.perform(get("/items"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.items.[0].id", is(id1.toString())))
                .andExpect(jsonPath("$._embedded.items.[0].name", is("milk tea")))
                .andExpect(jsonPath("$._embedded.items.[0].price", is(5.0)))
                .andExpect(jsonPath("$._embedded.items.[1].id", is(id2.toString())))
                .andExpect(jsonPath("$._embedded.items.[1].name", is("fruit tea")))
                .andExpect(jsonPath("$._embedded.items.[1].price", is(7.0)))
                .andExpect(jsonPath("$._embedded.items.[2].id", is(id3.toString())))
                .andExpect(jsonPath("$._embedded.items.[2].name", is("bubble tea")))
                .andExpect(jsonPath("$._embedded.items.[2].price", is(9.0)));
    }

    @Test
    void testFindById() throws Exception {
        when(findItem.findById(id1)).thenReturn(item1);

        mockMvc.perform(get("/items/{id}", id1.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("milk tea")))
                .andExpect(jsonPath("$.price", is(5.0)))
                .andExpect(jsonPath("$.store.name", is("bob")))
                .andExpect(jsonPath("$.store.location", is("queens")))
                .andExpect(jsonPath("$.store.id", is(storeId.toString())));

    }

    @Test
    void testFindByName() throws Exception {

        when(findItem.findByName("milk tea")).thenReturn(List.of(item1));

        mockMvc.perform(get("/items/?name=milk tea"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.items.[0].id", is(id1.toString())))
                .andExpect(jsonPath("$._embedded.items.[0].name", is("milk tea")))
                .andExpect(jsonPath("$._embedded.items.[0].price", is(5.0)))
                .andExpect(jsonPath("$._embedded.items.[0].store.name", is("bob")))
                .andExpect(jsonPath("$._embedded.items.[0].store.location", is("queens")))
                .andExpect(jsonPath("$._embedded.items.[0].store.id", is(storeId.toString())));

    }

    @Test
    void testFindByNameContaining() throws Exception {
        when(findItem.findByNameContaining("tea")).thenReturn(allItem);

        mockMvc.perform(get("/items/?name-contain=tea"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.items.[0].id", is(id1.toString())))
                .andExpect(jsonPath("$._embedded.items.[0].name", is("milk tea")))
                .andExpect(jsonPath("$._embedded.items.[0].price", is(5.0)))
                .andExpect(jsonPath("$._embedded.items.[1].id", is(id2.toString())))
                .andExpect(jsonPath("$._embedded.items.[1].name", is("fruit tea")))
                .andExpect(jsonPath("$._embedded.items.[1].price", is(7.0)))
                .andExpect(jsonPath("$._embedded.items.[2].id", is(id3.toString())))
                .andExpect(jsonPath("$._embedded.items.[2].name", is("bubble tea")))
                .andExpect(jsonPath("$._embedded.items.[2].price", is(9.0)));
    }

    @Test
    void testFindByStore() throws Exception {

        when(findItem.findByStore(eq(storeId))).thenReturn(allItem);

        mockMvc.perform(get("/stores/{storeId}/items", storeId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.items.[0].id", is(id1.toString())))
                .andExpect(jsonPath("$._embedded.items.[0].name", is("milk tea")))
                .andExpect(jsonPath("$._embedded.items.[0].price", is(5.0)))
                .andExpect(jsonPath("$._embedded.items.[1].id", is(id2.toString())))
                .andExpect(jsonPath("$._embedded.items.[1].name", is("fruit tea")))
                .andExpect(jsonPath("$._embedded.items.[1].price", is(7.0)))
                .andExpect(jsonPath("$._embedded.items.[2].id", is(id3.toString())))
                .andExpect(jsonPath("$._embedded.items.[2].name", is("bubble tea")))
                .andExpect(jsonPath("$._embedded.items.[2].price", is(9.0)));

    }

    @Test
    void testFindByPriceLessThanEqual() throws Exception {
        when(findItem.findByPriceLessThanEqual(8, true)).thenReturn(itemPrice);

        mockMvc.perform(get("/items/?price-leq=8&sorted=true", storeId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.items.[0].id", is(id1.toString())))
                .andExpect(jsonPath("$._embedded.items.[0].name", is("milk tea")))
                .andExpect(jsonPath("$._embedded.items.[0].price", is(5.0)))
                .andExpect(jsonPath("$._embedded.items.[1].id", is(id2.toString())))
                .andExpect(jsonPath("$._embedded.items.[1].name", is("fruit tea")))
                .andExpect(jsonPath("$._embedded.items.[1].price", is(7.0)));

    }

    @Test
    void testFindByAvgRatingGreaterThanEqual() throws Exception {
        when(findItem.findByAvgRatingGreaterThanEqual(0.3f, true)).thenReturn(itemRating);

        mockMvc.perform(get("/items/?rating-geq=0.3&sorted=true", storeId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))


                .andExpect(jsonPath("$._embedded.items.[0].id", is(id2.toString())))
                .andExpect(jsonPath("$._embedded.items.[0].name", is("fruit tea")))
                .andExpect(jsonPath("$._embedded.items.[0].price", is(7.0)))
                .andExpect(jsonPath("$._embedded.items.[1].id", is(id3.toString())))
                .andExpect(jsonPath("$._embedded.items.[1].name", is("bubble tea")))
                .andExpect(jsonPath("$._embedded.items.[1].price", is(9.0)));
    }

    @Test
    void testFindByRating() throws Exception {
        when(findItem.findByRating(ratingId)).thenReturn(item2);

        mockMvc.perform(get("/items/?ratingId={ratingId}", ratingId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$.id", is(id2.toString())))
                .andExpect(jsonPath("$.name", is("fruit tea")))
                .andExpect(jsonPath("$.price", is(7.0)));
    }

    @Test
    void testUpdateItem() throws Exception {
        SimpleItemDto item4 = new ItemDto("fruit tea", 7, 0);
        item2.setId(id1);
        item4.setId(id1);

        when(updateItem.updateItem(eq(item1), isA(SimpleItemDto.class))).thenReturn(item2);
        when(findItem.findById(id1)).thenReturn(item1);

        mockMvc.perform(put("/items/{id}", id1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(item4)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("fruit tea")))
                .andExpect(jsonPath("$.price", is(7.0)));
    }


}
