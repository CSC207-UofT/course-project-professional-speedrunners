package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.domain.builder.UserBuilder;
import com.boba.bobabuddy.core.service.rating.CreateRatingService;
import com.boba.bobabuddy.core.service.rating.FindRatingService;
import com.boba.bobabuddy.core.service.rating.RemoveRatingService;
import com.boba.bobabuddy.core.service.rating.UpdateRatingService;
import com.boba.bobabuddy.core.data.dto.RatingDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//integration testing without core and persistence layer
@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @MockBean
    private CreateRatingService createRating;
    @MockBean
    private FindRatingService findRating;
    @MockBean
    private RemoveRatingService removeRating;
    @MockBean
    private UpdateRatingService updateRating;

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
        email1 = "ye@gmail.com";
        user1 = new UserBuilder().setName("ye").setEmail(email1).setPassword("123").createUser();
        store1 = new Store("bob's", "st george");
        rating1 = new Rating(1, null, null);
        rating2 = new Rating(0, null, null);
        rating1.setRatableObject(item1);
        rating1.setUser(user1);
        rating1.setId(id1);
        rating2.setRatableObject(item1);
        rating2.setUser(user1);
        rating2.setId(id2);

    }


    @Test
    void testCreate() throws Exception {
        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();
        store1.setId(id1);

        RatingDto rating = new RatingDto();
        rating.setRating(1);

        Rating createdRating = new Rating(1, user1, store1);
        createdRating.setId(id1);
        when(createRating.create(any(), ratableType, eq(id1), eq(email1))).thenReturn(createdRating);

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
        ratings = Arrays.asList(rating1, rating2);
        when(findRating.findAll()).thenReturn((List<Rating>) ratings);

        mockMvc.perform(get("/ratings"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.ratings[0].rating", is(1)))
                .andExpect(jsonPath("$._embedded.ratings[1].rating", is(0)));
    }

    @Test
    void testFindByRatableObject() throws Exception {
        id1 = UUID.randomUUID();
        item1 = new Item(5, store1, "milk tea");
        item1.setId(id1);
        ratings = Set.of(rating1, rating2);
        item1.setRatings((Set<Rating>) ratings);

        when(findRating.findByItem(id1)).thenReturn((Set<Rating>) ratings);
        mockMvc.perform(get("/items/{id}/ratings", id1.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.ratings[0].rating", Matchers.isA(Integer.TYPE)))
                .andExpect(jsonPath("$._embedded.ratings[1].rating", Matchers.isA(Integer.TYPE)));

    }

    @Test
    void testFindByUser() throws Exception {
        ratings = Set.of(rating1, rating2);
        when(findRating.findByUser(email1)).thenReturn((Set<Rating>) ratings);
        mockMvc.perform(get("/users/{email}/ratings", email1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$._embedded.ratings[0].rating", Matchers.isA(Integer.TYPE)))
                .andExpect(jsonPath("$._embedded.ratings[1].rating", Matchers.isA(Integer.TYPE)));
    }





}
