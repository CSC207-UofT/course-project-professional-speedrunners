package com.boba.bobabuddy.infrastructure.dto;

public class RoleDto{
    private String name;

    public RoleDto(String name){this.name = name;}

    public RoleDto(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
