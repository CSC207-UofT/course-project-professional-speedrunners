package com.boba.bobabuddy.infrastructure.config;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.infrastructure.dto.RatableObjectDto;
import com.boba.bobabuddy.infrastructure.dto.StoreDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class SpringConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper =  new ModelMapper();
//        Converter<List<Item>, List<UUID>> itemListToIdConverter = ctx -> ctx.getSource()
//                .stream()
//                .map(Item::getId)
//                .collect(Collectors.toList());
//
//        Converter<Set<Rating>, Set<UUID>> ratingListToIdConverter = ctx -> ctx.getSource()
//                .stream()
//                .map(Rating::getId)
//                .collect(Collectors.toSet());
//
//
//        mapper.createTypeMap(Store.class, StoreDto.class).addMappings(map -> map
//                .using(itemListToIdConverter)
//                .map(Store::getMenu, StoreDto::setMenu));
//
//        mapper.createTypeMap(RatableObject.class, RatableObjectDto.class).addMappings(map -> map
//                .using(ratingListToIdConverter)
//                .map(RatableObject::getRatings, RatableObjectDto::setRatings));

        return mapper;
    }
}
