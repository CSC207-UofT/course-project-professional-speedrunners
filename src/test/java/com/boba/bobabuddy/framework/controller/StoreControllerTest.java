package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.service.store.CreateStoreService;
import com.boba.bobabuddy.core.service.store.FindStoreService;
import com.boba.bobabuddy.core.service.store.RemoveStoreService;
import com.boba.bobabuddy.core.service.store.UpdateStoreService;
import com.boba.bobabuddy.framework.assembler.StoreResourceAssembler;
import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StoreControllerTest {
    @MockBean
    private CreateStoreService createStore;

    @MockBean
    private FindStoreService findStore;

    @MockBean
    private UpdateStoreService updateStore;

    @MockBean
    private RemoveStoreService removeStore;

    @Autowired
    private StoreResourceAssembler assembler;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ObjectMapper modelMapper;

    private Store store1, store2, store3;
    private Rating rating;
    private Item item;
    private StoreDto createStoreRequest;
    private List<Store> allStoreLst, ratingLst, nameLst;
    private UUID id1, id2, id3, ratingId, itemId;

    @BeforeEach
    void setUp() {
        store1 = new Store("Blobs", "bloor");
        store2 = new Store("T", "bloor");
        store3 = new Store("Chatime", ":)");

        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();
        id3 = UUID.randomUUID();
        store1.setId(id1);
        store2.setId(id2);
        store3.setId(id3);

        store1.setAvgRating(1);
        store2.setAvgRating(0.5f);
        store3.setAvgRating(0.4f);

        item = new Item(2.0f, store1, "yum");
        itemId = UUID.randomUUID();
        item.setId(itemId);
        store1.addItem(item);

        rating = new Rating(1, null, store1);
        ratingId = UUID.randomUUID();
        rating.setId(ratingId);
        store1.addRating(rating);

        createStoreRequest = new StoreDto();
        createStoreRequest.setName("mmm");
        createStoreRequest.setLocation("map");

        allStoreLst = Arrays.asList(store1, store2, store3);
        ratingLst = Arrays.asList(store1, store2);
        nameLst = List.of(store1);
    }

    @AfterEach
    void teardown() {
        store1 = null;
        store2 = null;
        store3 = null;

        id1 = null;
        id2 = null;
        id3 = null;

        item = null;
        itemId = null;

        rating = null;
        ratingId = null;

        createStoreRequest = null;

        allStoreLst = null;
        ratingLst = null;
        nameLst = null;
    }

    @Test
    void testCreateStore() throws Exception{
        Store createdStore = new Store("mmm", "map");
        createdStore.setId(id1);
        when(createStore.create(ArgumentMatchers.isA(Store.class))).thenReturn(createdStore);

        mockMvc.perform(post("/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createStoreRequest)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("mmm")))
                .andExpect(jsonPath("$.location", is("map")));
    }

    @Test
    void testFindAll() throws Exception {
        when(findStore.findAll()).thenReturn(allStoreLst);

        mockMvc.perform(get("/stores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.stores.[0].id", is(id1.toString())))
                .andExpect(jsonPath("$._embedded.stores.[0].name", is("Blobs")))
                .andExpect(jsonPath("$._embedded.stores.[0].location", is("bloor")))
                .andExpect(jsonPath("$._embedded.stores.[1].id", is(id2.toString())))
                .andExpect(jsonPath("$._embedded.stores.[1].name", is("T")))
                .andExpect(jsonPath("$._embedded.stores.[1].location", is("bloor")))
                .andExpect(jsonPath("$._embedded.stores.[2].id", is(id3.toString())))
                .andExpect(jsonPath("$._embedded.stores.[2].name", is("Chatime")))
                .andExpect(jsonPath("$._embedded.stores.[2].location", is(":)")));
    }

    @Test
    void testFindById() throws Exception {
        when(findStore.findById(id1)).thenReturn(store1);

        mockMvc.perform(get("/stores/{id}", id1.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("Blobs")))
                .andExpect(jsonPath("$.location", is("bloor")));

    }

    @Test
    void testFindByItem() throws Exception {
        when(findStore.findByItem(eq(itemId))).thenReturn(store1);

        mockMvc.perform(get("/stores/?itemId={itemId}", itemId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("Blobs")))
                .andExpect(jsonPath("$.location", is("bloor")));
    }

    @Test
    void testFindByLocation() throws Exception {
        when(findStore.findByLocation("bloor", )).thenReturn(ratingLst);

        mockMvc.perform(get("/stores/?location=bloor"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.stores.[0].id", is(id1.toString())))
                .andExpect(jsonPath("$._embedded.stores.[0].name", is("Blobs")))
                .andExpect(jsonPath("$._embedded.stores.[0].location", is("bloor")))
                .andExpect(jsonPath("$._embedded.stores.[1].id", is(id2.toString())))
                .andExpect(jsonPath("$._embedded.stores.[1].name", is("T")))
                .andExpect(jsonPath("$._embedded.stores.[1].location", is("bloor")));

    }

    @Test
    void testFindByName() throws Exception {
        when(findStore.findByName("Blobs", )).thenReturn(nameLst);

        mockMvc.perform(get("/stores/?name=Blobs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.stores.[0].id", is(id1.toString())))
                .andExpect(jsonPath("$._embedded.stores.[0].name", is("Blobs")))
                .andExpect(jsonPath("$._embedded.stores.[0].location", is("bloor")));
    }

    @Test
    void testFindByNameContaining() throws Exception {
        when(findStore.findByNameContaining("s", )).thenReturn(nameLst);

        mockMvc.perform(get("/stores/?name-contain=s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.stores.[0].id", is(id1.toString())))
                .andExpect(jsonPath("$._embedded.stores.[0].name", is("Blobs")))
                .andExpect(jsonPath("$._embedded.stores.[0].location", is("bloor")));
    }

    @Test
    void testFindByAvgRatingGreaterThanEqual() throws Exception {
        when(findStore.findByAvgRatingGreaterThanEqual(0.5f, , true)).thenReturn(ratingLst);

        mockMvc.perform(get("/stores/?rating-geq=0.5&sorted=true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.stores.[0].id", is(id1.toString())))
                .andExpect(jsonPath("$._embedded.stores.[0].name", is("Blobs")))
                .andExpect(jsonPath("$._embedded.stores.[0].location", is("bloor")))
                .andExpect(jsonPath("$._embedded.stores.[1].id", is(id2.toString())))
                .andExpect(jsonPath("$._embedded.stores.[1].name", is("T")))
                .andExpect(jsonPath("$._embedded.stores.[1].location", is("bloor")));
    }

    @Test
    void testFindByRating() throws Exception {
        when(findStore.findByRating(ratingId)).thenReturn(store1);

        mockMvc.perform(get("/stores/?ratingId={ratingId}", ratingId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("Blobs")))
                .andExpect(jsonPath("$.location", is("bloor")));

    }

    @Test
    void testUpdateStore() throws Exception{
        StoreDto store4 = new StoreDto();
        store4.setName("T");
        store4.setLocation("bloor");

        store2.setId(id1);
        store4.setId(id1);

        when(updateStore.updateStore(eq(store1), isA(StoreDto.class))).thenReturn(store2);
        when(findStore.findById(id1)).thenReturn(store1);

        mockMvc.perform(put("/stores/{id}", id1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(store4)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("T")))
                .andExpect(jsonPath("$.location", is("bloor")));

    }

    @Test
    void testRemoveStore(){
        removeStore.removeById(id1);

        verify(removeStore).removeById(id1);
    }
}
