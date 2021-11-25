package com.boba.bobabuddy.infrastructure.config;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.RatableObject;
import com.boba.bobabuddy.core.entity.Role;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.infrastructure.dao.RoleJpaRepository;
import com.boba.bobabuddy.infrastructure.dto.BaseRatableObjectDto;
import com.boba.bobabuddy.infrastructure.dto.ItemDto;
import com.boba.bobabuddy.infrastructure.dto.RatableObjectDto;
import com.boba.bobabuddy.infrastructure.dto.StoreDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SpringConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Item.class, RatableObjectDto.class)
                .setConverter(mappingContext -> modelMapper.map(mappingContext.getSource(), StoreDto.class));
        modelMapper.createTypeMap(Store.class, RatableObjectDto.class)
                .setConverter(mappingContext -> modelMapper.map(mappingContext.getSource(), StoreDto.class));
        return modelMapper;
    }

}
