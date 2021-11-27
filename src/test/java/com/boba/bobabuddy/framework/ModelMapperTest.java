package com.boba.bobabuddy.framework;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class ModelMapperTest {

    private ModelMapper modelMapper;

//    @BeforeEach
//    void setup(){
//        modelMapper = new ModelMapper();
//        modelMapper.createTypeMap(Item.class, RatableObjectDto.class)
//                .setConverter(mappingContext -> modelMapper.map(mappingContext.getSource(), StoreDto.class));
//        modelMapper.createTypeMap(Store.class, RatableObjectDto.class)
//                .setConverter(mappingContext -> modelMapper.map(mappingContext.getSource(), StoreDto.class));
//    }

//    @Test
//    void mapRatable(){
//        Store store1 = new Store("bob", "stgeorge");
//        Rating rating1 = new Rating(1, null, store1);
//
//        RatingDto ratingDto = modelMapper.map(rating1, RatingDto.class);
//        Assertions.assertNotNull(ratingDto);
//
//    }
}
