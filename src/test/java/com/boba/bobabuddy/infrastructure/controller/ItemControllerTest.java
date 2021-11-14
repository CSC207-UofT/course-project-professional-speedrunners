package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.core.usecase.item.port.ICreateItem;
import com.boba.bobabuddy.core.usecase.item.port.IFindItem;
import com.boba.bobabuddy.core.usecase.item.port.IRemoveItem;
import com.boba.bobabuddy.core.usecase.item.port.IUpdateItem;
import com.boba.bobabuddy.core.usecase.request.CreateItemRequest;
import com.boba.bobabuddy.infrastructure.assembler.ItemResourceAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ItemController.class)
@Import(ItemResourceAssembler.class)
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

    Store store1;
    UUID storeId, id;
    CreateItemRequest item1;
    Item createdItem;

    @BeforeEach
    void setup(){
        store1 = new Store("bob", "queens");
        storeId = UUID.randomUUID();
        store1.setId(storeId);

        item1 = new CreateItemRequest();
        item1.setName("tea");
        item1.setPrice(1);

        createdItem = new Item(1, null, "tea");
        id = UUID.randomUUID();
        createdItem.setId(id);
        createdItem.setStore(store1);
    }

    @Test
    void testCreateItem() throws Exception {
        when(createItem.create(any(), eq(storeId))).thenReturn(createdItem);

        mockMvc.perform(post("/stores/{storeId}/items", storeId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(item1)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))

                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.name", is("tea")))
                .andExpect(jsonPath("$.price", is(1.0)))
                .andExpect(jsonPath("$.store.name", is("bob")))
                .andExpect(jsonPath("$.store.location", is("queens")))
                .andExpect(jsonPath("$.store.id", is(storeId.toString())));
    }



}
