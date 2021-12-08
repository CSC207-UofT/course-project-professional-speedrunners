package com.boba.bobabuddy.framework.util;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DtoConverterImpl<T, S> implements DtoConverter<T, S> {

    protected final ModelMapper modelMapper;
    protected final Class<T> entityType;
    private final Class<S> dtoType;


    public DtoConverterImpl(ModelMapper modelMapper, Class<T> entityType, Class<S> dtoType) {
        this.modelMapper = modelMapper;
        this.entityType = entityType;
        this.dtoType = dtoType;
    }

    @Override
    public S convertToDto(T entity) {
        return modelMapper.map(entity, dtoType);
    }

    @Override
    public List<S> convertToDtoList(Collection<T> entities) {
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public T convertToEntity(S dto) {
        return modelMapper.map(dto, entityType);
    }
}
