package com.boba.bobabuddy.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Objects matching corresponding entities in the domain layer
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor @NoArgsConstructor
@Jacksonized @Builder
public class CategoryDto {
    private UUID id;
    private String name;
    private Set<ItemDto> items;
}
