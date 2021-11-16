package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.item.port.ICreateItem;
import com.boba.bobabuddy.core.usecase.item.port.IFindItem;
import com.boba.bobabuddy.core.usecase.item.port.IRemoveItem;
import com.boba.bobabuddy.core.usecase.item.port.IUpdateItem;
import com.boba.bobabuddy.core.usecase.rating.port.ICreateRating;
import com.boba.bobabuddy.core.usecase.rating.port.IFindRating;
import com.boba.bobabuddy.core.usecase.rating.port.IRemoveRating;
import com.boba.bobabuddy.core.usecase.rating.port.IUpdateRating;
import com.boba.bobabuddy.infrastructure.assembler.ItemResourceAssembler;
import com.boba.bobabuddy.infrastructure.assembler.RatingResourceAssembler;
import com.boba.bobabuddy.infrastructure.config.SpringConfig;
import com.boba.bobabuddy.infrastructure.dto.ItemDto;
import com.boba.bobabuddy.infrastructure.dto.SimpleItemDto;
import com.boba.bobabuddy.infrastructure.dto.SimpleRatingDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//integration testing without core and persistence layer
@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @MockBean
    private ICreateRating createRating;
    @MockBean
    private IFindRating findRating;
    @MockBean
    private IRemoveRating removeRating;
    @MockBean
    private IUpdateRating updateRating;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;



    private Rating rating1, rating2;
    private Collection<Rating> ratings;
    private User user1;
    private Item item1;
    private Store store1;
    private UUID id1, id2;
    private String email1;

    @BeforeEach
    void setup(){
        rating1 = new Rating(1, null, null);
        rating2 = new Rating(0, null, null);
        email1 = "ye@gmail.com";
        user1 = new User("ye", email1, "123");
        store1 = new Store("bob's", "st george");
    }


    @Test
    void testCreate() throws Exception {
        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();
        store1.setId(id1);

        SimpleRatingDto rating = new SimpleRatingDto();
        rating.setRating(1);

        Rating createdRating = new Rating(1, user1, store1);
        createdRating.setId(id1);
        when(createRating.create(any(), eq(id1), eq(email1))).thenReturn(createdRating);

        mockMvc.perform(post("/{ratableObject}/{id}/ratings?createdBy=ye@gmail.com", "stores", id1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(rating)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.rating", is(1)))
                .andExpect(jsonPath("$.user.email", is(email1)))
                .andExpect(jsonPath("$.user.name", is("ye")))
                .andExpect(jsonPath("$.user.password", is("123")))
                .andExpect(jsonPath("$.ratableObject.name", is("bob's")))
                .andExpect(jsonPath("$.ratableObject.location", is("st george")));
    }

    @Test
    void testFindAll() throws Exception {
        rating1.setRatableObject(item1);
        rating1.setUser(user1);
        rating1.setId(id1);
        rating2.setRatableObject(item1);
        rating2.setUser(user1);
        rating2.setId(id2);



        ratings = Arrays.asList(rating1, rating2);
        when(findRating.findAll()).thenReturn((List<Rating>) ratings);

        mockMvc.perform(get("/ratings"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.ratings[0].rating", is(1)))
                .andExpect(jsonPath("$._embedded.ratings[1].rating", is(0)));

    }
}
