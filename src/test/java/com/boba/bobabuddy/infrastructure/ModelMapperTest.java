package com.boba.bobabuddy.infrastructure;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.infrastructure.dto.RatableObjectDto;
import com.boba.bobabuddy.infrastructure.dto.RatingDto;
import com.boba.bobabuddy.infrastructure.dto.StoreDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(MockitoExtension.class)
public class ModelMapperTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setup(){
        modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Item.class, RatableObjectDto.class)
                .setConverter(mappingContext -> modelMapper.map(mappingContext.getSource(), StoreDto.class));
        modelMapper.createTypeMap(Store.class, RatableObjectDto.class)
                .setConverter(mappingContext -> modelMapper.map(mappingContext.getSource(), StoreDto.class));
    }

    @Test
    void mapRatable(){
        Store store1 = new Store("bob", "stgeorge");
        Rating rating1 = new Rating(1, null, store1);

        RatingDto ratingDto = modelMapper.map(rating1, RatingDto.class);
        Assertions.assertNotNull(ratingDto);

    }
}
