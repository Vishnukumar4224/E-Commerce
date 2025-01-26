package com.ecommerce.backend.Security.Dto;

import com.ecommerce.backend.Security.Enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String email;

    private String name;

    private UserRole userRole;

}
