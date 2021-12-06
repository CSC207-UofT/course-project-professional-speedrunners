package com.boba.bobabuddy.framework.config;

import com.boba.bobabuddy.core.data.dto.*;
import com.boba.bobabuddy.core.domain.*;
import com.boba.bobabuddy.framework.converter.DtoConverterImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DtoConverterConfig {

    private final ModelMapper modelMapper;

    @Autowired
    public DtoConverterConfig(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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
