package com.boba.bobabuddy.infrastructure.controller;

import com.boba.bobabuddy.infrastructure.dto.ItemDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SpringBootTest
public class DeserializationTest {

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testDeserialize() throws JsonProcessingException {
        String json = "{\n" +
                "  \"id\": \"35fa9310-a72a-4573-bd0a-6760bd873d95\",\n" +
                "  \"name\": \"not Pearl Latte\",\n" +
                "  \"price\": 5.5,\n" +
                "  \"avgRating\": 0.0,\n" +
                "  \"store\": {\n" +
                "    \"id\": \"8f4ca86c-ee1f-4fa9-859c-410d03d25341\",\n" +
                "    \"name\": \"Kung Fu Tea on Spadina\",\n" +
                "    \"location\": \"264 Spadina Ave., Toronto, ON M5T 2E4\",\n" +
                "    \"menu\": [\n" +
                "      {\n" +
                "        \"id\": \"35fa9310-a72a-4573-bd0a-6760bd873d95\",\n" +
                "        \"name\": \"not Pearl Latte\",\n" +
                "        \"price\": 5.5,\n" +
                "        \"avgRating\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"2818cd8e-0167-4a10-a03a-a3a5d9959637\",\n" +
                "        \"name\": \"Pearl Black Tea\",\n" +
                "        \"price\": 5.7,\n" +
                "        \"avgRating\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"af3051de-4b71-4e64-85a0-931344824e98\",\n" +
                "        \"name\": \"Pearl Chocolate Latte\",\n" +
                "        \"price\": 5.7,\n" +
                "        \"avgRating\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"36e83670-8aec-4e0b-9a41-4987ad4ce85b\",\n" +
                "        \"name\": \"Pearl Matcha Latte\",\n" +
                "        \"price\": 6.0,\n" +
                "        \"avgRating\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"d7746cfe-fb9b-4a24-b6dc-5af7ce1e4f14\",\n" +
                "        \"name\": \"Pearl Taro Latte\",\n" +
                "        \"price\": 6.0,\n" +
                "        \"avgRating\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"8f3e10ba-90d6-43f0-9e4e-1482310fdb1a\",\n" +
                "        \"name\": \"actually not Pearl Latte\",\n" +
                "        \"price\": 5.5,\n" +
                "        \"avgRating\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"1d434d4f-c235-4de3-9a0d-beb861fff3a9\",\n" +
                "        \"name\": \"actually really not Pearl Latte\",\n" +
                "        \"price\": 5.5,\n" +
                "        \"avgRating\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"269dde4a-12e0-41b7-bcbb-5f7ba34c3a36\",\n" +
                "        \"name\": \"actually is Pearl Latte\",\n" +
                "        \"price\": 5.5,\n" +
                "        \"avgRating\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"3c7adfad-26e6-49b4-a5d5-b5eb1c6c0046\",\n" +
                "        \"name\": \"actually is Pearl Latte\",\n" +
                "        \"price\": 5.5,\n" +
                "        \"avgRating\": 0.0\n" +
                "      }\n" +
                "    ],\n" +
                "    \"ratings\": []\n" +
                "  },\n" +
                "  \"ratings\": []\n" +
                "}";

        ItemDto itemDto = mapper.readValue(json, ItemDto.class);
        Assertions.assertNotNull(itemDto);
    }


}
