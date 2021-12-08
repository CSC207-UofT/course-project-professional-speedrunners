package com.boba.bobabuddy.framework.controller;

import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Rating;
import com.boba.bobabuddy.core.domain.Store;
import com.boba.bobabuddy.core.domain.User;
import com.boba.bobabuddy.core.service.rating.CreateRatingService;
import com.boba.bobabuddy.core.service.rating.FindRatingService;
import com.boba.bobabuddy.core.service.rating.RemoveRatingService;
import com.boba.bobabuddy.core.service.rating.UpdateRatingService;
import com.boba.bobabuddy.core.data.dto.RatingDto;
import com.boba.bobabuddy.framework.converter.DtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.util.*;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//integration testing without core and persistence layer
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RatingController.class,
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class) },
        excludeAutoConfiguration = { SecurityAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class RatingControllerTest {

    @MockBean
    private CreateRatingService createRating;
    @MockBean
    private FindRatingService findRating;
    @MockBean
    private RemoveRatingService removeRating;
    @MockBean
    private UpdateRatingService updateRating;
    @MockBean
    private DtoConverter<Rating, RatingDto> converter;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;



    private Rating rating1, rating2;
    private RatingDto ratingDto1, ratingDto2;
    private Collection<Rating> ratings;
    private List<RatingDto> ratingDtos;
    private User user1;
    private Item item1;
    private Store store1;
    private UUID id1, id2;
    private String email1;

    @BeforeEach
    void setup(){
        email1 = "ye@gmail.com";
        user1 = User.builder().name("ye").email(email1).password("123").build();
        store1 = Store.builder().name("bob's").location("st george").build();
        rating1 = Rating.builder().rating(1).id(id1).build();
        ratingDto1 = RatingDto.builder().rating(1).id(id1).build();
        rating2 = Rating.builder().rating(0).id(id2).build();
        ratingDto2 = RatingDto.builder().rating(0).id(id2).build();
        ratingDtos = Arrays.asList(ratingDto1, ratingDto2);
        item1 = Item.builder().price(5).store(store1).name("milk tea").build();
    }


    @Test
    void testCreate() throws Exception {
        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();
        store1.setId(id1);

        RatingDto rating = new RatingDto();
        rating.setRating(1);

        Rating createdRating = Rating.builder().rating(1).id(id1).build();
        RatingDto ratingDto = RatingDto.builder().rating(1).id(id1).build();
        when(createRating.create(any(), any(), eq(id1), eq(email1))).thenReturn(createdRating);
        when(converter.convertToDto(createdRating)).thenReturn(ratingDto);

        mockMvc.perform(post("/{ratableObject}/{id}/ratings?createdBy=ye@gmail.com", "stores", id1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(rating)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is(id1.toString())))
                .andExpect(jsonPath("$.rating", is(1)));
    }

    @Test
    void testFindAll() throws Exception {
        ratings = Arrays.asList(rating1, rating2);
        when(findRating.findAll()).thenReturn((List<Rating>) ratings);
        when(converter.convertToDtoList(any())).thenReturn(ratingDtos);

        mockMvc.perform(get("/ratings"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].rating", is(1)))
                .andExpect(jsonPath("$[1].rating", is(0)));
    }

    @Test
    void testFindByRatableObject() throws Exception {
        id1 = UUID.randomUUID();
        item1.setId(id1);
        ratings = Set.of(rating1, rating2);
        item1.setRatings((Set<Rating>) ratings);

        when(findRating.findByItem(id1)).thenReturn((Set<Rating>) ratings);
        when(converter.convertToDtoList(any())).thenReturn(ratingDtos);
        mockMvc.perform(get("/items/{id}/ratings", id1.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].rating", Matchers.isA(Integer.TYPE)))
                .andExpect(jsonPath("$[1].rating", Matchers.isA(Integer.TYPE)));

    }

    @Test
    void testFindByUser() throws Exception {
        ratings = Set.of(rating1, rating2);
        when(findRating.findByUser(email1)).thenReturn((Set<Rating>) ratings);
        when(converter.convertToDtoList(any())).thenReturn(ratingDtos);

        mockMvc.perform(get("/users/{email}/ratings", email1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].rating", Matchers.isA(Integer.TYPE)))
                .andExpect(jsonPath("$[1].rating", Matchers.isA(Integer.TYPE)));
    }





}
