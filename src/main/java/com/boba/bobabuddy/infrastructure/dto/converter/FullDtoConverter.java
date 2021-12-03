package com.boba.bobabuddy.infrastructure.dto.converter;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Full-featured DTO converter that also converts simple DTO types that does not hold nested reference
 * @param <T> Entity type
 * @param <S> Simple DTO type
 * @param <D> DTO type
 */
public class FullDtoConverter<T, S, D extends S> extends DtoConverter<T, D> {
    private final Class<S> simpleDtoType;

    public FullDtoConverter(ModelMapper modelMapper, Class<T> entityType, Class<S> simpleDtoType, Class<D> dtoType) {
        super(modelMapper, entityType, dtoType);
        this.simpleDtoType = simpleDtoType;
    }

    public S convertToSimpleDto(T entity) {
        return modelMapper.map(entity, simpleDtoType);
    }

    public Collection<S> convertToSimpleDtoCollection(Collection<T> entities) {
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public T convertToEntityFromSimple(S dto) {
        return modelMapper.map(dto, entityType);
    }
}
