package com.boba.bobabuddy.framework.config;

import com.boba.bobabuddy.core.data.dto.CategoryDto;
import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.data.dto.StoreDto;
import com.boba.bobabuddy.core.domain.Category;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.domain.Store;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Store.class, StoreDto.class)
                .addMappings(mapping -> mapping.using(new ItemToDtoConverter()).map(Store::getMenu, StoreDto::setMenu));
        modelMapper.createTypeMap(Item.class, ItemDto.class)
                .addMappings(mapping -> mapping.using(new CategoryToDtoConverter()).map(Item::getCategories, ItemDto::setCategories));
        modelMapper.createTypeMap(Category.class, CategoryDto.class)
                .addMappings(mapping -> mapping.using(new ItemToDtoConverter()).map(Category::getItems, CategoryDto::setItems));

        return modelMapper;
    }

    public static class ItemToDtoConverter implements Converter<Collection<Item>, Collection<ItemDto>> {

        @Override
        public Collection<ItemDto> convert(MappingContext<Collection<Item>, Collection<ItemDto>> context) {
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

    public static class CategoryToDtoConverter implements Converter<Collection<Category>, Collection<CategoryDto>> {

        @Override
        public Collection<CategoryDto> convert(MappingContext<Collection<Category>, Collection<CategoryDto>> context) {
            return context.getSource().stream()
                    .map(this::convertItem)
                    .collect(Collectors.toList());
        }

        private CategoryDto convertItem(Category category) {

            return CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();

        }
    }

}
