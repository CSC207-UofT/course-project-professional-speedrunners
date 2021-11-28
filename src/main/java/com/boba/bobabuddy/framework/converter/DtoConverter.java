package com.boba.bobabuddy.framework.converter;

import java.util.Collection;
import java.util.List;

public interface DtoConverter<T, S> {
    S convertToDto(T entity);

    List<S> convertToDtoList(Collection<T> entities);

    T convertToEntity(S dto);
}
