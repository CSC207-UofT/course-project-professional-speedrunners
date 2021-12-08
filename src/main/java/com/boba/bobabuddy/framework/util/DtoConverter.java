package com.boba.bobabuddy.framework.util;

import java.util.Collection;
import java.util.List;

/**
 * Helper class to convert one generic type to another
 * @param <T> Entity type
 * @param <S> DTO type
 */
public interface DtoConverter<T, S> {
    /**
     * Convert entity to its DTO representation
     * @param entity entity object
     * @return DTO with type S
     */
    S convertToDto(T entity);

    /**
     * Convert List of entities to list of  DTO representations
     * @param entities list of entity objects
     * @return DTOs
     */
    List<S> convertToDtoList(Collection<T> entities);

    /**
     * Map DTO to its entity counterpart
     * @param dto DTO
     * @return Corresponding entity
     */
    T convertToEntity(S dto);
}
