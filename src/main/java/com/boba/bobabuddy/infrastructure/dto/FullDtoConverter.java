package com.boba.bobabuddy.infrastructure.dto;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FullDtoConverter<T, S, D extends S> extends DtoConverter<T, D> {
    private final Class<S> simpleDtoType;

    public FullDtoConverter(ModelMapper modelMapper, Class<T> entityType, Class<S> simpleDtoType, Class<D> dtoType) {
        super(modelMapper, entityType, dtoType);
        this.simpleDtoType = simpleDtoType;
    }

    public S convertToSimpleDto(T entity){
        return modelMapper.map(entity, simpleDtoType);
    }

    public Collection<S> convertToSimpleDtoCollection(Collection<T> entities){
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public T convertToEntityFromSimple(S dto){
        return modelMapper.map(dto, entityType);
    }
}
