package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.data.dto.RatingDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.service.store.CreateStoreService;
import com.boba.bobabuddy.core.service.store.FindStoreService;
import com.boba.bobabuddy.core.service.store.RemoveStoreService;
import com.boba.bobabuddy.core.service.store.UpdateStoreService;
import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.boba.bobabuddy.framework.converter.DtoConverter;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = StoreController.class,
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class) },
        excludeAutoConfiguration = { SecurityAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class StoreControllerTest {
    @MockBean
    private CreateStoreService createStore;

    @MockBean
    private FindStoreService findStore;

    @MockBean
    private UpdateStoreService updateStore;

    @MockBean
    private RemoveStoreService removeStore;

    @MockBean
    private DtoConverter<Store, StoreDto> converter;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ObjectMapper modelMapper;

    private Store store1, store2, store3;
    private StoreDto storeDto1, storeDto2, storeDto3;
    private Rating rating;
    private RatingDto ratingDto;
    private Item item;
    private ItemDto itemDto;
    private StoreDto createStoreRequest;
    private List<Store> allStoreLst, ratingLst, nameLst;
    private List<StoreDto> allStoreLstDto, ratingLstDto, nameLstDto;
    private UUID id1, id2, id3, ratingId, itemId;

    @BeforeEach
    void setUp() {
        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();
        id3 = UUID.randomUUID();
        store1 = Store.builder()
                .name("Blobs")
                .location("bloor")
                .id(id1)
                .avgRating(1)
                .build();

        storeDto1 = StoreDto.builder()
                .name("Blobs")
                .location("bloor")
                .id(id1)
                .avgRating(1)
                .build();

        store2 = Store.builder()
                .name("T")
                .location("bloor")
                .id(id2)
                .avgRating(0.5)
                .build();
        storeDto2 = StoreDto.builder()
                .name("T")
                .location("bloor")
                .id(id2)
                .avgRating(0.5)
                .build();

        store3 = Store.builder()
                .name("Chatime")
                .location( ":)")
                .id(id3)
                .avgRating(0.4)
                .build();
        storeDto3 = StoreDto.builder()
                .name("Chatime")
                .location( ":)")
                .id(id3)
                .avgRating(0.4)
                .build();


        itemId = UUID.randomUUID();

        item = Item.builder()
                .price(2.0)
                .id(itemId)
                .name("yum").build();
        itemDto = ItemDto.builder()
                .price(2.0)
                .id(itemId)
                .name("yum").build();

        store1.addItem(item);
        storeDto1.addItem(itemDto);

        ratingId = UUID.randomUUID();
        rating = Rating.builder().rating(1).id(ratingId).build();
        ratingDto = RatingDto.builder().rating(1).id(ratingId).build();
        store1.addRating(rating);
        storeDto1.addRating(ratingDto);

        createStoreRequest = new StoreDto();
        createStoreRequest.setName("mmm");
        createStoreRequest.setLocation("map");

        allStoreLst = Arrays.asList(store1, store2, store3);
        allStoreLstDto = Arrays.asList(storeDto1, storeDto2, storeDto3);
        ratingLst = Arrays.asList(store1, store2);
        ratingLstDto = Arrays.asList(storeDto1, storeDto2);
        nameLst = List.of(store1);
        nameLstDto = List.of(storeDto1);
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
        Store createdStore = Store.builder().name("mmm").location("map").build();
        StoreDto createdDto = StoreDto.builder().name("mmm").location("map").build();
        createdStore.setId(id1);
        createdDto.setId(id1);

        when(createStore.create(ArgumentMatchers.any())).thenReturn(createdStore);
        when(converter.convertToDto(ArgumentMatchers.any())).thenReturn(createdDto);

        mockMvc.perform(post("/admin/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createdDto)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("mmm")))
                .andExpect(jsonPath("$.location", is("map")));
    }

    @Test
    void testFindAll() throws Exception {
        when(findStore.findAll(ArgumentMatchers.any())).thenReturn(allStoreLst);
        when(converter.convertToDtoList(ArgumentMatchers.any())).thenReturn(allStoreLstDto);

        mockMvc.perform(get("/stores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("Blobs")))
                .andExpect(jsonPath("$[0].location", is("bloor")))
                .andExpect(jsonPath("$[1].id", is(id2.toString())))
                .andExpect(jsonPath("$[1].name", is("T")))
                .andExpect(jsonPath("$[1].location", is("bloor")))
                .andExpect(jsonPath("$[2].id", is(id3.toString())))
                .andExpect(jsonPath("$[2].name", is("Chatime")))
                .andExpect(jsonPath("$[2].location", is(":)")));
    }

    @Test
    void testFindById() throws Exception {
        when(findStore.findById(id1)).thenReturn(store1);
        when(converter.convertToDto(any())).thenReturn(storeDto1);

        mockMvc.perform(get("/stores/{id}", id1.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("Blobs")))
                .andExpect(jsonPath("$.location", is("bloor")));

    }

    @Test
    void testFindByItem() throws Exception {
        when(findStore.findByItem(eq(itemId))).thenReturn(store1);
        when(converter.convertToDto(any())).thenReturn(storeDto1);

        mockMvc.perform(get("/stores/?itemId={itemId}", itemId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.name", is("Blobs")))
                .andExpect(jsonPath("$.location", is("bloor")));
    }

    @Test
    void testFindByLocation() throws Exception {
        when(findStore.findByLocation(eq("bloor"), any())).thenReturn(ratingLst);
        when(converter.convertToDtoList(any())).thenReturn(ratingLstDto);
        mockMvc.perform(get("/stores/?location=bloor"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("Blobs")))
                .andExpect(jsonPath("$[0].location", is("bloor")))
                .andExpect(jsonPath("$[1].id", is(id2.toString())))
                .andExpect(jsonPath("$[1].name", is("T")))
                .andExpect(jsonPath("$[1].location", is("bloor")));

    }

    @Test
    void testFindByName() throws Exception {
        when(findStore.findByName(eq("Blobs"), any())).thenReturn(nameLst);
        when(converter.convertToDtoList(any())).thenReturn(nameLstDto);

        mockMvc.perform(get("/stores/?name=Blobs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("Blobs")))
                .andExpect(jsonPath("$[0].location", is("bloor")));
    }

    @Test
    void testFindByNameContaining() throws Exception {
        when(findStore.findByNameContaining(eq("s"), any())).thenReturn(nameLst);
        when(converter.convertToDtoList(any())).thenReturn(nameLstDto);


        mockMvc.perform(get("/stores/?name-contain=s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("Blobs")))
                .andExpect(jsonPath("$[0].location", is("bloor")));
    }

    @Test
    void testFindByAvgRatingGreaterThanEqual() throws Exception {
        when(findStore.findByAvgRatingGreaterThanEqual(eq(0.5), any())).thenReturn(ratingLst);
        when(converter.convertToDtoList(any())).thenReturn(ratingLstDto);

        mockMvc.perform(get("/stores/?rating-geq=0.5&sorted=true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].id", is(id1.toString())))
                .andExpect(jsonPath("$[0].name", is("Blobs")))
                .andExpect(jsonPath("$[0].location", is("bloor")))
                .andExpect(jsonPath("$[1].id", is(id2.toString())))
                .andExpect(jsonPath("$[1].name", is("T")))
                .andExpect(jsonPath("$[1].location", is("bloor")));
    }

    @Test
    void testFindByRating() throws Exception {
        when(findStore.findByRating(ratingId)).thenReturn(store1);
        when(converter.convertToDto(any())).thenReturn(storeDto1);

        mockMvc.perform(get("/stores/?ratingId={ratingId}", ratingId.toString()))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

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

        when(updateStore.updateStore(eq(id1), isA(StoreDto.class))).thenReturn(store2);
        when(findStore.findById(id1)).thenReturn(store1);
        when(converter.convertToDto(any())).thenReturn(store4);

        mockMvc.perform(put("/stores/{id}", id1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(store4)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

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
