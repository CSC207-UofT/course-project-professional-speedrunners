package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.store.port.ICreateStore;
import com.boba.bobabuddy.core.usecase.store.port.IFindStore;
import com.boba.bobabuddy.core.usecase.store.port.IRemoveStore;
import com.boba.bobabuddy.core.usecase.store.port.IUpdateStore;
import com.boba.bobabuddy.infrastructure.assembler.StoreResourceAssembler;
import com.boba.bobabuddy.infrastructure.dto.StoreDto;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StoreControllerTest {
    @MockBean
    private ICreateStore createStore;

    @MockBean
    private IFindStore findStore;

    @MockBean
    private IUpdateStore updateStore;

    @MockBean
    private IRemoveStore removeStore;

    @Autowired
    private StoreResourceAssembler assembler;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ObjectMapper modelMapper;

    private Store store;
    private StoreDto createStoreRequest;
    private List<Store> allStore;
    private UUID id, id1;

    @BeforeEach
    void setUp() {
        store = new Store("Blobs", "bloor");
        id = UUID.randomUUID();
        store.setId(id);

        createStoreRequest = new StoreDto();
        createStoreRequest.setName("mmm");
        createStoreRequest.setLocation("map");

        id1 = UUID.randomUUID();

        allStore = Arrays.asList(store);
    }

    @AfterEach
    void teardown() {
        store = null;
    }

    @Test
    void createStore() throws Exception{
        Store createdStore = new Store("mmm", "map");
        createdStore.setId(id);
        when(createStore.create(ArgumentMatchers.isA(Store.class))).thenReturn(createdStore);

        mockMvc.perform(post("/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createStoreRequest)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.name", is("mmm")))
                .andExpect(jsonPath("$.location", is("map")));
    }

    @Test
    void findAll() throws Exception {
        when(findStore.findAll()).thenReturn(allStore);

        mockMvc.perform(get("/stores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.stores.[0].id", is(id.toString())))
                .andExpect(jsonPath("$._embedded.stores.[0].name", is("Blobs")))
                .andExpect(jsonPath("$._embedded.stores.[0].location", is("bloor")));
    }

    @Test
    void findById(){

    }

    @Test
    void findByItem(){

    }

    @Test
    void findByLocation(){

    }

    @Test
    void findByName(){

    }

    @Test
    void findByNameContaining(){

    }

    @Test
    void findByAvgRatingGreaterThanEqual(){

    }

    @Test
    void findByRating(){

    }

    @Test
    void UpdateStore(){

    }

    @Test
    void removeStore(){

    }
}
