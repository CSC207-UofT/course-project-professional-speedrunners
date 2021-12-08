package com.boba.bobabuddy.framework.config;

import com.boba.bobabuddy.core.data.dto.*;
import com.boba.bobabuddy.core.domain.*;
import com.boba.bobabuddy.framework.util.DtoConverterImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to create DTO to Entity converter beans for each controller to autowire
 * This is not the same converter as defined in ModelMapper config. Model Mapper converter is a component of the library
 * ModelMapper, while DtoConverter is a generic consumer of the said library.
 */

@Configuration
public class DtoConverterConfig {

    private final ModelMapper modelMapper;

    @Autowired
    public DtoConverterConfig(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Bean
    public DtoConverterImpl<Category, CategoryDto> categoryDtoConverter(){
        return new DtoConverterImpl<>(modelMapper, Category.class, CategoryDto.class);
    }
    @Bean
    public DtoConverterImpl<Item, ItemDto> itemDtoConverter() {
        return new DtoConverterImpl<>(modelMapper, Item.class, ItemDto.class);
    }

    @Bean
    public DtoConverterImpl<Store, StoreDto> storeDtoConverter() {
        return new DtoConverterImpl<>(modelMapper, Store.class, StoreDto.class);
    }

    @Bean
    public DtoConverterImpl<Rating, RatingDto> ratingDtoConverter() {
        return new DtoConverterImpl<>(modelMapper, Rating.class, RatingDto.class);
    }

    @Bean
    public DtoConverterImpl<Role, RoleDto> roleDtoConverter() {
        return new DtoConverterImpl<>(modelMapper, Role.class, RoleDto.class);
    }

    @Bean
    public DtoConverterImpl<User, UserDto> userDtoConverter() {
        return new DtoConverterImpl<>(modelMapper, User.class, UserDto.class);
    }
}
