package com.boba.bobabuddy.core.data.dto;

import com.boba.bobabuddy.framework.validator.ValidEmail;
import com.boba.bobabuddy.framework.validator.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Objects matching the corresponding entities in the domain layer
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor @NoArgsConstructor
@Jacksonized @Builder
public class UserDto {
    @ValidEmail
    @NotNull
    @NotEmpty
    private UUID id;
    private String email;
    @NotNull
    @NotEmpty
    private String name;
    @ValidPassword
    @NotNull
    @NotEmpty
    private String password;
    private Set<RatingDto> ratings;
    private Collection<RoleDto> roles;
}
