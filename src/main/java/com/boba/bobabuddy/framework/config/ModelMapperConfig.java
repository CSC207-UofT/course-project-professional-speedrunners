package com.boba.bobabuddy.framework.config;

import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Store;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Store.class, StoreDto.class)
                .addMappings(mapping -> mapping.using(new ItemToDtoConverter()).map(Store::getMenu, StoreDto::setMenu));
        return modelMapper;
    }

    public static class ItemToDtoConverter implements Converter<List<Item>, List<ItemDto>> {

        @Override
        public List<ItemDto> convert(MappingContext<List<Item>, List<ItemDto>> context) {
            return context.getSource().stream()
                    .map(this::convertItem)
                    .collect(Collectors.toList());
        }

        private ItemDto convertItem(Item item) {

            return ItemDto.builder()
                    .name(item.getName())
                    .id(item.getId())
                    .avgRating(item.getAvgRating())
                    .price(item.getPrice())
                    .build();

        }
    }

}
