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

/**
 * Configuration for ModelMapper to customize entity to DTO conversion behaviour.
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        //Under Store to StoreDTO context, back reference to store in each item within the menu will be dropped
        modelMapper.createTypeMap(Store.class, StoreDto.class)
                .addMappings(mapping -> mapping.using(new ItemToDtoConverter()).map(Store::getMenu, StoreDto::setMenu));
        //Under Item to ItemDTO context, back reference to Item in each Category object within the item will be dropped
        modelMapper.createTypeMap(Item.class, ItemDto.class)
                .addMappings(mapping -> mapping.using(new CategoryToDtoConverter()).map(Item::getCategories, ItemDto::setCategories));
        //Under Category to CategoryDto context, back reference to store and category in each item object within the category will be dropped
        //WIP, The interactions with Category is not ideal.
        modelMapper.createTypeMap(Category.class, CategoryDto.class)
                .addMappings(mapping -> mapping.using(new ItemToDtoConverter()).map(Category::getItems, CategoryDto::setItems));

        return modelMapper;
    }

    public static class ItemToDtoConverter implements Converter<Collection<Item>, Collection<ItemDto>> {

        @Override
        public List<ItemDto> convert(MappingContext<Collection<Item>, Collection<ItemDto>> context) {
            return context.getSource().stream()
                    .map(this::convertItem)
                    .collect(Collectors.toList());
        }

        private ItemDto convertItem(Item item) {

            return ItemDto.builder()
                    .name(item.getName())
                    .id(item.getId())
                    .avgRating(item.getAvgRating())
                    .imageUrl(item.getImageUrl())
                    .price(item.getPrice())
                    .build();
        }
    }

    public static class CategoryToDtoConverter implements Converter<Collection<Category>, Collection<CategoryDto>> {

        @Override
        public List<CategoryDto> convert(MappingContext<Collection<Category>, Collection<CategoryDto>> context) {
            if(context.getSource() == null){return null;}

            return context.getSource().stream()
                    .map(this::convertCategory)
                    .collect(Collectors.toList());
        }

        private CategoryDto convertCategory(Category category) {

            return CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();

        }
    }

}
