package com.boba.bobabuddy.infrastructure.dto.converter;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
public class DtoConverter<T, S> {

    protected final ModelMapper modelMapper;
    protected final Class<T> entityType;
    private final Class<S> dtoType;


    public DtoConverter(ModelMapper modelMapper, Class<T> entityType, Class<S> dtoType) {
        this.modelMapper = modelMapper;
        this.entityType = entityType;
        this.dtoType = dtoType;
    }


    public S convertToDto(T entity){
        return modelMapper.map(entity, dtoType);
    }

    public Collection<S> convertToDtoCollection(Collection<T> entities){
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public T convertToEntity(S dto){
        return modelMapper.map(dto, entityType);
    }
}
